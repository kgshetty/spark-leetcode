package com.scala.sample.practice

import scala.io.Source
import java.util.HashMap

import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode

object PracticeBasic2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("PracticeBasic2")
      .getOrCreate();
    spark.sparkContext.setLogLevel("ERROR");
    import spark.implicits._

    //reading the input data into a dataframe
    var inputdata = spark.read.format("csv").option("delimiter", ",").option("header", "true").
      option("inferSchema", "true").load("src/main/resources/question2.txt")
    //loading the data into inut hive table
    inputdata.write.format("parquet").saveAsTable("input2")

    //reading data from hive table to dataframe
    var userActivity = spark.sql("select timestamp as click_time,userid from input2")
    userActivity.show()
    val inactverule1: Long = 30 * 60

    //window function for partitioning the data by userid
    val userwin = Window.partitionBy("userid").orderBy("click_time")
    
    
    
    val temp1 = userActivity.
      withColumn("ts_diff", unix_timestamp($"click_time") - unix_timestamp(
        lag($"click_time", 1).over(userwin))).
      withColumn("ts_diff", when(row_number.over(userwin) === 1 || $"ts_diff" >= inactverule1, 0L).
        otherwise($"ts_diff"))

    temp1.show(false)
    temp1.
      groupBy("userid").agg(
        collect_list($"click_time").as("click_list"), collect_list($"ts_diff").as("ts_list")).show(false)

    var finalres = temp1.
      groupBy("userid").agg(
        collect_list($"click_time").as("click_list"), collect_list($"ts_diff").as("ts_list")).
        withColumn(
          "click_sess_id",
          explode(enrichdata()($"userid", $"click_list", $"ts_list"))).
          select($"userid", $"click_sess_id._1".as("click_time"), $"click_sess_id._2".as("sess_id"))

    // adding year , month and day columns for  partitioning
    finalres = finalres.withColumn("sess_date", to_date($"click_time")).
      withColumn("year", year($"click_time")).withColumn("month", month($"click_time")).
      withColumn("day", dayofmonth($"click_time"))

    //partitiong the data by year,month,day for better performance
    finalres.repartition(1).write.partitionBy("year", "month", "day").
      format("parquet").mode(SaveMode.Overwrite).saveAsTable("result2")

    var userSession = spark.sql("select * from result2")
    userSession.show(false)

    userSession.registerTempTable("input3")
    //total number of sessions in a single day
    spark.sql("select sess_date as Date , count(distinct(sess_id)) as sessionCount from input3 group by sess_date").show()

    //total time spent a user in a single day
    spark.sql("""select sess_date ,  userid , sum(time_spent)/60 as time_spent_min from (select userid , sess_date , sess_id , max(unix_timestamp(click_time)) - min(unix_timestamp(click_time)) as time_spent 
                  from input3  group by sess_date , userid , sess_id) a group by sess_date ,  userid""").show()

    //total time spent by a user in a month
    spark.sql("""select year,month ,  userid , sum(time_spent)/60 as time_spent_min from (select userid , year , month , sess_id , max(unix_timestamp(click_time)) - min(unix_timestamp(click_time)) as time_spent 
                  from input3  group by year , month , userid , sess_id) a group by year , month ,  userid """).show()
  }

  /*  Udf to generate the session id
   *  Inputs :- UserId,List of click timestamps , List of TimeDifferences
   *  OutPut :- Session ids
   *
   *  */
  def enrichdata() = udf { (uid: String, cl: Seq[String], tsl: Seq[Long]) =>
    val maxSessionTime: Long = 2 * 60 * 60
    def sessionid(n: Long) = s"$uid-$n"

    val list = tsl.foldLeft((List[String](), 0L, 0L)) {
      case ((ls, j, k), i) =>
        print("i = "+i)
        if (i == 0 || j + i >= maxSessionTime) (sessionid(k + 1) :: ls, 0L, k + 1) else
          (sessionid(k) :: ls, j + i, k)
    }._1.reverse

    cl zip list
  }
}