package com.scala.ds.algo.array

object TestEbay {
  def main(args: Array[String]): Unit = {
    
    var data = Array("karthik","srinu")
    
    var stringMax_length = 0;
    
    for(data1 <- data)
    {
      if(stringMax_length < data1.length)
        stringMax_length = data1.length
    }
    
    println(stringMax_length)
    var strbul = new StringBuilder()
    for(i <- 0 to stringMax_length)
    {
      for(data1 <- data)
      {
        if(i<data1.length())
        {
          strbul.append(data1(i))
        }
      }
    }
    println(strbul.toString())
   
  }
  
  def test() : Unit = {
     println("in ebay test")
    var data = "aaabaaaabb"
    
    var test = data.map(x => x.toByte).toList
    println(test)
    var list :List[String] = List()
    var k = 1
    var lastsplit = 0
    for(i <- 0 to test.size-2)
    {
      if(! (test(i)- test(i+1)  <= k ))
      {
        list ++ List(data.slice(lastsplit, i))
        
        lastsplit = i
        println()
      }
        
        
    }
    
    println(list)
  }
}