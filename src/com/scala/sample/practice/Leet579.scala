package com.scala.sample.practice


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Leet579 {
  def main(args : Array[String]) : Unit={
    println("testing leet code 579")
    val spark = SparkSession.builder().
      master("local[1]").
      appName("test").
      getOrCreate()
    import spark.implicits._
    var input_file = spark.read.json("D:\\interview_data\\leetcode_579.txt")
    input_file.printSchema 
    var columns = input_file.select("headers.Employee").collect()(0).getSeq[String](0).toList
    
    
    var raw_records = input_file.select(explode($"rows.Employee"))
    
    
    var df = columns.zipWithIndex.foldLeft(raw_records){
      (datadf,column) =>
        {
          datadf.withColumn(column._1 ,col("col")(column._2))
        }
    }.drop("col")
    df.printSchema
    df.show(10)
    println(df.count())
    
    df.registerTempTable("employee")
    
    var res = spark.sql("""
      
        select e.Id, e.month, sum(e1.salary) as salary from 
          employee e join employee e1 
        on e.id = e1.id and e.month >= e1.month and e.month-e1.month <= 2 
        where e.month < (select max(month) from Employee where id = e.id)
        group by e.Id, e.month order by e.Id , e.month desc
        
      """)
      
      
       var res1 = spark.sql("""
      
      select b.id,b.month , (salary+pre_1_salary+pre_2_salary) as salary from 
      (
          select e.id,e.month,e.salary,
          case when lag(e.month,1) over(partition by e.id order by e.month) = e.month-1 then lag(e.salary,1) over(partition by e.id order by e.month) else 0 end as pre_1_salary,
          case when lag(e.month,2) over(partition by e.id order by e.month) = e.month-2 then lag(e.salary,2) over(partition by e.id order by e.month) else 0 end as pre_2_salary
      
          from Employee e left join 
          (
              select id ,max(month) as month from Employee group by id
          ) a on e.id = a.id and e.month = a.month where a. id is null order by e.id,e.month
      ) b order by id , month desc
      """)
      
    println("result count = "+res.count())    
    res.show(1000,false)
    
  }
}