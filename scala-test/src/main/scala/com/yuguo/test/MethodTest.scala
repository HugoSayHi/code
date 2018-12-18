package com.yuguo.test

object MethodTest {

  def main(args: Array[String]): Unit = {

    delayed(time())


  }


  def time() = {
    println("获取时间")
    System.nanoTime()
  }

  def delayed(t: => Long) = {
    println("delayed 方法内")
    println("time = " + t)
    t
  }


}
