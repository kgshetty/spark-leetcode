package com.scala.sample.practice


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Leet2199 {
  def main(args : Array[String]) : Unit={
    println("testing leet code 1112")
    val spark = SparkSession.builder().
      master("local[1]").
      appName("test").
      getOrCreate()
    import spark.implicits._
    var input_file = spark.read.json("D:\\interview_data\\leetcode_2199.txt")
    input_file.printSchema 
    var columns = input_file.select("headers.Keywords").collect()(0).getSeq[String](0).toList
    
    
    var raw_records=input_file.select(explode($"rows.Keywords"))
    
    
    var df = columns.zipWithIndex.foldLeft(raw_records){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    df.printSchema
    df.show(10)
    println(df.count())
    
    df.registerTempTable("Keywords")
    
    
    var columns1 = input_file.select("headers.Posts").collect()(0).getSeq[String](0).toList
    
    
    var raw_records1=input_file.select(explode($"rows.Posts"))
    
    
    var df1 = columns1.zipWithIndex.foldLeft(raw_records1){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    df1.printSchema
    df1.show(10)
    println(df1.count())
    
    df1.registerTempTable("Posts")
    
    var data1 = spark.range(1000)
    data1.show(10)
    var res5 = data1.head()
    print(res5)
    
   /* spark.sql("""
      select post_id , case when length(top)=0 then 'Ambiguous!' else top end as topic from 
      (select post_id  , trim(concat_ws(',' , array_sort(collect_set(topic_id)))) as top  from
      (select post_id , explode(split(lower(content),' ')) as token from Posts) a left join Keywords b on (a.token = lower(b.word))
      group by post_id order by post_id) c
      
      """).show(100,false)
     */
     
    
    
    
  }
}