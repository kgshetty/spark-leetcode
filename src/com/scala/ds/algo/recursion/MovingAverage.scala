package com.scala.ds.algo.recursion
import scala.util.control.Breaks._
import scala.collection.mutable.Map

object MovingAverage {
  def main(args: Array[String]): Unit = {
    
    var data = Array("ES,2000,5,1;ES,2030,15,2;ES,2000,10,1;SPY,2050,15,5;ES,2067,8,6;SPY,2050,5,7" , "ES,2000,5,1;SPY,2050,15,2", "ES,2000,5,2;ES,2050,5,4" , "ES,2000,5,3;SPY,2040,5,2")
    // 
  for( i <- 0 until data.length)
  {
    println(data(i))
    var traList = data(i).split(";")
    println(traList.toList)
    var dataMap : scala.collection.mutable.Map[String,(Double,Double)] = scala.collection.mutable.Map()
    
    var currentSeq = -1
    
   
      for(j <- 0 to traList.length-1)
    {
       var key = traList(j).split(",")(0)
       var value = traList(j).split(",")(1).toInt
       var quantity = traList(j).split(",")(2).toInt
       var sequence = traList(j).split(",")(3).toInt
      // println("sequence = "+sequence+"  currentSeq = "+currentSeq)
       if(sequence > currentSeq)
       {
         currentSeq = sequence
       
         if(dataMap.contains(key))
         {
          
           var exis_dataMap = dataMap.get(key).get
           var wma = ((exis_dataMap._1 * exis_dataMap._2)+(value * quantity))/(exis_dataMap._2+quantity)
            dataMap += (key -> (wma , (exis_dataMap._2+quantity)))
            println(key+": "+BigDecimal(wma).setScale(2 , BigDecimal.RoundingMode.HALF_UP).toDouble)
         }
         else
         {
            
            var wma = (value * quantity)/quantity
            dataMap += (key -> (wma , quantity))
            println(key+": "+BigDecimal(wma).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble)
         }
       }
         
        
    }
    
    println("*********************************************************************")
  }
    
  }
}