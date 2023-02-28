package com.scala.sample.practice


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Leet1635 {
  def main(args : Array[String]) : Unit={
    println("testing leet code 1635")
    val spark = SparkSession.builder().
      master("local[1]").
      appName("test").
      getOrCreate()
    import spark.implicits._
    var input_file = spark.read.json("D:\\interview_data\\leetcode_1635.txt")
    input_file.printSchema 
    
    var driver_columns = input_file.select("headers.Drivers").collect()(0).getSeq[String](0).toList
    var raw_records_driver=input_file.select(explode($"rows.Drivers"))
    var driver_df = driver_columns.zipWithIndex.foldLeft(raw_records_driver){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    driver_df.printSchema
    driver_df.show(10)
    println(driver_df.count())
    driver_df.registerTempTable("Drivers")
    
    
    var rides_columns = input_file.select("headers.Rides").collect()(0).getSeq[String](0).toList
    var raw_records_rides=input_file.select(explode($"rows.Rides"))
    var rides_df = rides_columns.zipWithIndex.foldLeft(raw_records_rides){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    rides_df.printSchema
    rides_df.show(10)
    println(rides_df.count())
    rides_df.registerTempTable("Rides")
    
    
    var ar_rides_columns = input_file.select("headers.AcceptedRides").collect()(0).getSeq[String](0).toList
    var raw_records_ar_rides=input_file.select(explode($"rows.AcceptedRides"))
    var ar_rides_df = ar_rides_columns.zipWithIndex.foldLeft(raw_records_ar_rides){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    ar_rides_df.printSchema
    ar_rides_df.show(10)
    println(ar_rides_df.count())
    ar_rides_df.registerTempTable("AcceptedRides")
    var month_df = spark.range(1,13).withColumnRenamed("id", "month")
    month_df.show(100,false)
    
    
    spark.sql("""
      select year(join_date)  as year , month(join_date) as month , sum(1) over(order by join_date) as driver_count from Drivers group by year(join_date),month(join_date)
      """).show(false)
    
    
     
     
    
    
    
  }
}