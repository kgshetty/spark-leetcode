package com.scala.sample.practice


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Leet1949 {
  def main(args : Array[String]) : Unit={
    println("testing leet code 1112")
    val spark = SparkSession.builder().
      master("local[1]").
      appName("test").
      getOrCreate()
    import spark.implicits._
    var input_file = spark.read.json("D:\\interview_data\\leetcode_1949.txt")
    input_file.printSchema 
    var columns = input_file.select("headers.Friendship").collect()(0).getSeq[String](0).toList
    
    
    var raw_records=input_file.select(explode($"rows.Friendship"))
    
    
    var df = columns.zipWithIndex.foldLeft(raw_records){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    df.printSchema
    df.show(10)
    println(df.count())
    
    df.registerTempTable("Friendship")
    
    spark.sql("""
          
            select f1.user1_id , f1.user2_id  , f2.user2_id , count(*) as cnt from  Friendship f1 join Friendship f2 on f1.user1_id = f2.user1_id or f1.user2_id = f2.user1_id
            
            where f1.user1_id = 1 and f1.user2_id = 3
            group by f1.user1_id , f1.user2_id  , f2.user2_id 
            order by f1.user1_id , f1.user2_id
    
     """).show(100,false)
     
     
    
    
    
  }
}