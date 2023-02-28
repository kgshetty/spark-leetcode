package com.scala.sample.practice

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.expressions.Window

object Test {
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

    /*val events = spark.read.format("csv").option("inferSchema","true").option("header","true").load("D:\\Interview_data\\events.csv")
    events.printSchema
    val cat = spark.read.format("csv").option("inferSchema","true").option("header","true").load("D:\\Interview_data\\category_tree.csv")
    cat.printSchema()
   val url = "jdbc:postgresql://localhost/test"
    //df2.write.mode(SaveMode.Append).jdbc(url,"test1",connectionProperties)
*/
    val events = spark.read.format("csv").option("inferSchema","true").option("header","true").load("D:\\Interview_data\\events.csv")
   // val events = spark.read.format("csv").option("inferSchema", "true").option("header", "true").load("src/main/resources/events_test_data.txt")
    events.printSchema()
    events.show(10, false)
   //events.describe("event","itemid").show(false)
   events.summary("approx_count_distinct").show(false)

  }
}