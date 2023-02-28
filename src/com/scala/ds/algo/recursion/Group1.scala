package com.scala.ds.algo.recursion

object Group1 {
  
  def main(args: Array[String]): Unit = {
    /*println("in main")
    println("power = "+power(2,4))
    println("factorial = "+factorial(7))
    println("recursiveRange = "+recursiveRange(10))
    println("fib = "+fib(35))
    println("productOfArray = "+productOfArray(Array(1,2,3,10)))
    println("reverse = "+reverse("kihtrak"))
     println("reverse = "+isPalindrome("oyo"))*/
    

    println(myPow(2.00000,-2147483647))
  }
  
    def myPow(x: Double, n: Int): Double = {
        if(n==0 || x==1){
            return 1
        }
        else if(n<0)
        return 1/myPow(x , -n)
        else{
            if(n%2 == 0) 
                return myPow(x*x , n/2)
            else
                return x*myPow(x*x , (n-1)/2)
        }
            
    }

  def isPalindrome(strng:String):Boolean = {
    return reverse(strng).equals(strng)
  }
  
  def reverse(strng :String) : String = {
    if(strng.length()==1)
    {
      return strng
    }
    else
    {
      return strng.charAt(strng.length()-1)+reverse(strng.dropRight(1))
    }
    
  }
  
  def productOfArray(arr:Array[Int]):Int = {
    if(arr.length==0)
    {
      return 1
    }
    else arr(0)*productOfArray(arr.drop(1))
  }
  
  def fib(num:Int):Int = {
    if(num == 0 || num == 1){
      return num
    }
    else {
      return fib(num-1)+fib(num-2)
    }
  }
  
  def recursiveRange(num:Int) : Int = {
    if(num<1)
    {
      return 0
    }
    else {
      return num+recursiveRange(num-1)
    }
  }
  
  def factorial(n:Int):Int = {
    if(n<=1)
    {
      return 1
    }
    else{
      return n*factorial(n-1)
    }
  }
  
  def power(base:Int , exponent:Int) :Int = {
    
    if(exponent == 0)
    {
      return 1
    }
    else{
      return base * power(base , exponent-1)
    }
    
  }
  
}