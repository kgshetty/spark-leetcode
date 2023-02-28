package com.scala.sample.practice

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.expressions._
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row


class CommonUtils {
  
  def getTopProdforweek(events : Dataset[Row] , spark: SparkSession) : Dataset[Row] = {
    import spark.implicits._
    
    events.printSchema()
   events.show(10,false)
   val data_events = events.withColumn("time_1", from_unixtime(col("timestamp")/1000))
   
   //events.select("event").distinct.show()
   //view
   data_events.printSchema
   data_events.registerTempTable("events_modified")
   
  // val result = spark.sql("select itemid , cnt from (select itemid,count(*) as cnt from events_modified where event = 'view' group by itemid) order by cnt desc").limit(5)
  // result.show()
   
   var weekData = data_events.withColumn("week", weekofyear(col("time_1")))
   
   weekData.registerTempTable("weekdata")
   var weekTopProd = spark.sql(" select week , itemid , view_count , dense_rank() over (partition by  week order by view_count desc) as rnk from (select week , itemid , count(*) as view_count  from weekdata where event = 'view' group by  week , itemid ) ").filter("rnk <=3 ")
   //weekTopProd.show(100,false)
   
   val userwin = Window.partitionBy("week").orderBy(col("view_count").desc)
   weekData.printSchema()
   var out = weekData.where(col("event") === "view").groupBy(col("week") , col("itemid")).agg(count(col("event")).as("view_count")).withColumn("rnk", rank().over(userwin)).filter("rnk <=3 ")
    
   return out;
  }
  
}