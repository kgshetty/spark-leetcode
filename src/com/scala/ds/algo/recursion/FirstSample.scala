package com.scala.ds.algo.recursion

object FirstSample {
  def main(args: Array[String]): Unit = {
    println("Hello World")
    //firstRecursion(4)
   // println(PowerOfTwoRecursion(0))
    //println(PowerOfTwoIterative(0))
    //println(factorialIterative(-1))
   // println(sumOfDigits(-1234))
   // println(powerOf(10,100))
    //print(gcd(18,-48))
   print(binary(13))
  }
  
  
  def binary(a :Int):Int = {
    if(a/2 == 0)
    {
      return a
    }
    else
    {
      return binary(a/2)*10+a%2
    }
    
    
  }
  
  
  def gcd(a :Int , b:Int):Int = {
    if(a%b == 0)
    {
      return b
    }
    else
    {
     return gcd(b,a%b)
    }
    
    
  }
  
  def powerOf(base : Int , exp : Int) : BigInt = {
    
    if(exp == 0){
      return 1
    }else if(exp == 1){
      return base
    }
    else 
    return  base * powerOf(base , exp-1)
   
  }
  
  def sumOfDigits(n : Int) : Int = {
    if(n<10){
      return n;
    }
    else {
      return n%10 + sumOfDigits(n/10)
    }
    
  }

  def factorialRecursion(n: Int): Int = {
    if(n == 0 || n==1 || n<0)
    {
      return 1
    }
    else
      return n*factorialRecursion(n-1)

  }
  
  def factorialIterative(n : Int) :Int = {
    var fact = 1
    for(a <- 1 to n)
    {
      fact = fact * a
    }
    
    return fact
  }

  def firstRecursion(a: Int): Unit = {
    if (a == 1) {
      println(a)
    } else {
      firstRecursion(a - 1)
      println(a)
    }

  }

  def PowerOfTwoRecursion(n: Int): Int = {
    if (n == 0) {
      println("at resursion end")
      return 1
    } else {
      var temp = PowerOfTwoRecursion(n - 1)
      println("at loop in n= " + n + " temp =  " + temp)
      return temp * 2
    }
  }

  def PowerOfTwoIterative(n: Int): Int = {
   
    var power = 1
    for (a <- 1 to n) {
      println("in iterative temp value = " + power)
      power = power * 2
    }
    return power
  }

}