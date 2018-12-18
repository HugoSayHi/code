package com.yuguo.test

object Test {


  def main(args: Array[String]): Unit = {
    println("hello world")
    println(testFunc("world", 2))
    println_(1)

  }

  //方法定义由一个 def 关键字开始，紧接着是可选的参数列表，一个冒号 : 和方法的返回类型，一个等于号 = ，最后是方法的主体
  def testFunc(val1 :String,val2 :Int) : String = {

    return "hello "+val1+val2
  }

  //如果方法没有返回值，可以返回为 Unit，这个类似于 Java 的 void,
  def println_(msg:Any) : Unit = {
    println(msg)
  }



}
