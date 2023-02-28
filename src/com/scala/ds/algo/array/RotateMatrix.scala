package com.scala.ds.algo.array

object RotateMatrix {
  def main(args: Array[String]): Unit = {

    println("in matrix rotation")

    val matrix: Array[Array[Int]] = Array(Array(1, 2, 3 ,4), Array( 5, 6 ,7,8), Array(9,10,11,12) , Array(13,14,15,16))
     val matrix1: Array[Array[Int]] = Array(Array(1, 2, 3 ), Array( 4,5, 6 ), Array(7,8,9) )
     val matrix2:Array[Array[Int]] = Array(Array(1,2),Array(3,4))
    printMatrix(matrix1)
    println("********************* result")
    var res = rotateMatrix(matrix2)
    println("********************* result")
     //printMatrix(res)

  }
  
  def printMatrix(matrix: Array[Array[Int]]): Unit = {
    for (i <- 0 to matrix.length - 1) {
      for (j <- 0 to matrix(0).length - 1) {
        print(matrix(i)(j) + " ")
      }
      println()
    }
  }
  
  def rotateMatrix(matrix: Array[Array[Int]]): Array[Array[Int]] = {

    var (top, left, right, bottom) = (0, 0, matrix.length - 1, matrix.length - 1)

    for(j <- 0 to matrix.length/2 )
    {
      println("in j="+j)
    }
    
    for(i <- 0 to matrix.length - 2)
    {
      println("in i = " + i)
      var tempVal = matrix(top)(left + i)
      println(tempVal)
      matrix(top)(left + i) = matrix(bottom - i)(left)
      matrix(bottom - i)(left) = matrix(bottom)(right - i)
      matrix(bottom)(right - i) = matrix(top + i)(right)
      matrix(top + i)(right) = tempVal
     
    }
    
     println("********************* result")
      printMatrix(matrix)
    
    

    /* matrix(left+1)(top) = matrix(bottom-1)(left)
    matrix(bottom-1)(left) = matrix(bottom)(right-1)
    matrix(bottom)(right-1) = matrix(top+1)(right)
    matrix(top+1)(right) = tempVal*/

   

    return matrix
  }
  
}