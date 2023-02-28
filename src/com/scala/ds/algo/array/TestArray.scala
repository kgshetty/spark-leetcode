package com.scala.ds.algo.array

object TestArray {

  //1.Create an array and traverse.
  //2. Access individual elements through indexes
  //3.Append any value to the array using append()method
  //4.Insert value in an array using insert()method
  //5. Extend python array using extend()method
  //6. Add items from list into array using fromlist()method
  //7. Remove any array element using remove()method
  //8. Remove last array element using pop()method
  //9. Fetch any element through its index using index()method
  //10. Reverseapython array using reverse()method
  //11. Get array buffer information through buffer_info()method
  //12. Check for number of occurrences of an element using count()method
  //13. Convert array to string using tostring()method
  //14. Convert array toapython Iist with same elements using tolist()method
  //15. Appendastring to char array using fromstring()method
  //16. Slice Elements from an array
  def main(args: Array[String]): Unit = {

    var myarray: Array[Int] = Array(1, 2, 3, 4, 5)

    for (x <- myarray) {
      println(x)
    }

    for (i <- 0 to myarray.length - 1) {
      println("data at " + i + " is " + myarray(i))
    }
    myarray = myarray ++ Array(6)
    myarray = myarray :+ 7
    
   
    var listdata : List[Int] = List(8,9,10,11,12,13,14)
    myarray = myarray++ listdata.toArray[Int]
    
    myarray =  myarray.patch(0,Array(0),0)
    for (x <- myarray) {
      println(x)
    }
    var temp = myarray.toBuffer
    temp.remove(myarray.indexOf(10))
    myarray = temp.toArray
     for (x <- myarray) {
      println(x)
    }
    var revarray = myarray.reverse
    
    for(x <- revarray)
    {
      println(x)
    }
    
    println(revarray.count(x => x%2 == 0))
     println(revarray.toString())
     var lis = revarray.toList
     println(lis)
    for(x <-  revarray.slice(5, 10))
    {
      println(x)
    }
   
    
  }
}