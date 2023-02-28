package com.scala.sample.practice


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Leet1079 {
  def main(args : Array[String]) : Unit={
    println("testing leet code 579")
    val spark = SparkSession.builder().
      master("local[1]").
      appName("test").
      getOrCreate()
    import spark.implicits._
    var input_file = spark.read.json("D:\\interview_data\\leetcode_1709.txt")
    input_file.printSchema 
    var columns = input_file.select("headers.UserVisits").collect()(0).getSeq[String](0).toList
    
    
    var raw_records=input_file.select(explode($"rows.UserVisits"))
    
    
    var df = columns.zipWithIndex.foldLeft(raw_records){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    df.printSchema
    df.show(10)
    println(df.count())
    
    df.registerTempTable("UserVisits")
    
    spark.sql("""
      
      select a.user_id , max(datediff(coalesce(b.visit_date , '2021-1-1') , a.visit_date)) as biggest_windows from 
      (select u1.user_id,u1.visit_date , count(*) as rnk  from UserVisits u1 join  UserVisits u2 on (u1.user_id = u2.user_id and u1.visit_date >= u2.visit_date) 
      group by u1.user_id,u1.visit_date
      order by u1.user_id , u1.visit_date ) a
      left join 
      (select u1.user_id,u1.visit_date , count(*) as rnk  from UserVisits u1 join  UserVisits u2 on (u1.user_id = u2.user_id and u1.visit_date >= u2.visit_date) 
      group by u1.user_id,u1.visit_date
      order by u1.user_id , u1.visit_date ) b 
      on (a.user_id = b.user_id and b.rnk = a.rnk+1) 
      group by a.user_id order by user_id
      
      
      
      
      
      """).show(100,false)
    
    
    
  }
}