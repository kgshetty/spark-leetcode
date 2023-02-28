package com.scala.sample.practice


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Leet2152 {
  def main(args : Array[String]) : Unit={
    println("testing leet code 1112")
    val spark = SparkSession.builder().
      master("local[1]").
      appName("test").
      getOrCreate()
    import spark.implicits._
    var input_file = spark.read.json("D:\\interview_data\\leetcode_2152.txt")
    input_file.printSchema 
    var columns = input_file.select("headers.Buses").collect()(0).getSeq[String](0).toList
    
    
    var raw_records=input_file.select(explode($"rows.Buses"))
    
    
    var df = columns.zipWithIndex.foldLeft(raw_records){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    df.printSchema
    df.show(10)
    println(df.count())
    
    df.registerTempTable("Buses")
    
    
    var columns1 = input_file.select("headers.Passengers").collect()(0).getSeq[String](0).toList
    
    
    var raw_records1=input_file.select(explode($"rows.Passengers"))
    
    
    var df1 = columns1.zipWithIndex.foldLeft(raw_records1){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    df1.printSchema
    df1.show(10)
    println(df1.count())
    
    df1.registerTempTable("Passengers")
    
    spark.sql("""
      
      select bus_id , arrival_time , lag(arrival_time,1) over (order by arrival_time ) from Buses order by arrival_time
      
      """).show(false)
     
     
    
    
    
  }
}