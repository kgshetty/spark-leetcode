package com.scala.sample.practice

import scala.io.Source
import java.util.HashMap
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
object PracticeBasic1 {
   def main(args: Array[String]): Unit = {
     
     
     val spark = SparkSession.builder()
      .master("local[1]")
      .appName("PracticeBasic2")
      .getOrCreate();
    spark.sparkContext.setLogLevel("ERROR");
    import spark.implicits._
    
    var df1 = spark.range(10).toDF("col1")
    
    var df2 = spark.range(20).toDF("col2").filter("col2 > 10")
    
    df1.registerTempTable("table1")
    df2.registerTempTable("table2")
    
    var res = spark.sql("select * from table1 a join table2 b on a.col1 != b.col2")
    res.show()
     println(res.count())
     /*
     
     //println("in practice")
      Reading the data from a file and converting data to set to remove the duplicate values
     var file_data = Source.fromFile("src/main/resources/question1.txt.txt").getLines().toList
     var temp_data =   file_data.map(x => (x.split(",")(0) , x.split(",")(1)))
     temp_data.toSet.toList.foreach(println)
     //
     var file_data2 = Source.fromFile("src/main/resources/question1.txt.txt").getLines().toList
     var hmap : scala.collection.immutable.Map[(String,String),Int] = Map()
     
     // 2nd way
     println("second way")
     
     for( e <- file_data2 )
     {
       if(hmap.get((e.split(",")(0) , e.split(",")(1)))==None)
       {
        hmap = hmap + ((e.split(",")(0),e.split(",")(1)) ->1)
       }
      
     }
     hmap.keys.toList.foreach(println)
    
  */} 
}