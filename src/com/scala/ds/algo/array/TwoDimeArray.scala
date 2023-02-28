package com.scala.ds.algo.array

import scala.util.Random

object TwoDimeArray {
  def main(args: Array[String]): Unit = {
    /*println("enter Range = ")
    var range = scala.io.StdIn.readInt()
   var dataList = 0 to range
   println(dataList.toList)
   var arrayBf = dataList.toArray.toBuffer
   var ran = Random.shuffle(dataList).take(1).toList(0)
   println(ran)
   arrayBf.remove(ran)
   var newArray = arrayBf.toArray*/
   
   //print(findMissingNumber(newArray,range))
    
    var nums = Array(1,2,3,4,5,6,7,8,9,10,10)
    var target = 6
  /*  var res = twoSum(nums , target)
    println(res.toList)*/
    
   /* var max = nums.max
    var buf = nums.toBuffer
    buf.remove(nums.indexOf(max))
    var temp = buf.toArray
    var max_1 = temp.max
    println(max + "*" +max_1 + " = "+max*max_1)*/
   
    println(containsDuplicate(nums))
     println(nums.length)
     println(nums.toSet.size)
     
     nums.toList.sorted
  }

  def containsDuplicate1(nums: Array[Int]): Boolean = {
    var temp: Array[Int] = Array()
    for (n <- 0 to nums.length - 1) {
      if (temp.contains(nums(n))) {
        return true
      }
      temp = temp ++ Array(nums(n))

    }
   
    return false
  }
  def containsDuplicate(nums: Array[Int]): Boolean = {
    
    return if(nums.toSet.size == nums.length) false else true
  }
  
  def findMissingNumber(array:Array[Int] , n:Int) :Int = {
    var arraySum = array.sum
    var actualSum = (n*(n+1))/2
    return actualSum-arraySum
  }
  
  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
        for(n <- 0 to nums.length-1)
        {
          for(m <- n+1 to nums.length-1)
          {
            if (nums(n)+nums(m) == target)
              return Array(n,m)
          }
        }
        return Array()
        
    }
  
}