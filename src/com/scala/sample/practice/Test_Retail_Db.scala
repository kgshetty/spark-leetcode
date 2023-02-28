package com.scala.sample.practice

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.expressions.Window
import scala.collection.JavaConverters._

object Test_Retail_Db {
  def main(args: Array[String]): Unit = {
    print("testing")
    val spark = SparkSession.builder().
      master("local[1]").
      appName("test").
      getOrCreate()
    import spark.implicits._
    val df = spark.range(100);
    import java.util.Properties
    val connectionProperties = new Properties()

    connectionProperties.put("user", "postgres")
    connectionProperties.put("password", "mysecretpassword")
    val url = "jdbc:postgresql://localhost/test"
    //spark.sparkContext.setLogLevel("ERROR")

    /*
    //df2.write.mode(SaveMode.Append).jdbc(url,"test1",connectionProperties)
		*/
    /*val orders = spark.read.
                  format("csv").
                  option("inferSchema", "false").
                  option("header", "false").
                  load("D:\\Interview_data\\cert_dat\\retail_db\\orders\\part-00000")
    orders.printSchema()
    orders.show(10, false)
    println(orders.count())
    orders.describe("*").show(false)*/
    
    val data = spark.read.json("D:\\Interview_data\\leetcode.txt")
   val columns = data.select("headers.Activity").collect()(0).getSeq[String](0).toList
   
   var raw_data = data.select(explode($"rows.Activity"))
    println(raw_data.schema)

    var output = columns.zipWithIndex.foldLeft(raw_data) {
      (memodDF, column) =>
        {
          memodDF.withColumn(column._1, col("col")(column._2))
        }
    }

   println( output.count)
    output.drop("col").show(false)
    output.createTempView("Activity")
   println( output.select("player_id").distinct().count)
    var res = spark.sql(""" 
      select *   from 
(
select player_id  , event_date , lead(event_date) over(partition by player_id order by event_date) as next_date  from Activity 
) a 
where DATEDIFF(next_date,event_date)=1
        """)
       println(res.select("player_id").distinct().count) 
     res.show(100,false)

  }
}