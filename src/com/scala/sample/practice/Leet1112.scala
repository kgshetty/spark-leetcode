package com.scala.sample.practice


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Leet1112 {
  def main(args : Array[String]) : Unit={
    println("testing leet code 1112")
    val spark = SparkSession.builder().
      master("local[1]").
      appName("test").
      getOrCreate()
    import spark.implicits._
    var input_file = spark.read.json("D:\\interview_data\\leetcode_1112.txt")
    input_file.printSchema 
    var columns = input_file.select("headers.Enrollments").collect()(0).getSeq[String](0).toList
    
    
    var raw_records=input_file.select(explode($"rows.Enrollments"))
    
    
    var df = columns.zipWithIndex.foldLeft(raw_records){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    df.printSchema
    df.show(10)
    println(df.count())
    
    df.registerTempTable("Enrollments")
    
    spark.sql("""
      
      select b.student_id , b.course_id , b.grade from
      
     (select e1.student_id , e1.course_id , e1.grade , count(*) as cnt
     
       from Enrollments e1 join Enrollments e2 
     
     on (e1.student_id = e2.student_id and e1.grade >= e2.grade)
     
     group by e1.student_id , e1.course_id , e1.grade) b join 
     
    ( select student_id , max(cnt) as max_cnt from 
     (select e1.student_id , e1.course_id , e1.grade , count(*) as cnt
     
       from Enrollments e1 join Enrollments e2 
     
     on (e1.student_id = e2.student_id and e1.grade >= e2.grade)
     
     group by e1.student_id , e1.course_id , e1.grade) a1 group by student_id) a
     
     on (b.student_id = a.student_id and b.cnt = a.max_cnt)
    
     
      
        order by b.student_id , b.course_id , b.grade
     """)//.show(100,false)
     
     spark.sql("""
       
      select e1.student_id , e1.course_id , e1.grade , count(*) as cnt
     
       from Enrollments e1 join Enrollments e2 
     
     on (e1.student_id = e2.student_id and e1.grade >= e2.grade)
     
     group by e1.student_id , e1.course_id , e1.grade
       
       """).show(100,false)
    
    
    
  }
}