package com.yuguo

import com.alibaba.fastjson.{JSON, JSONObject}


object Test {


  def main(args: Array[String]): Unit = {
    println("hello world")
//
//    val myMap: Map[String, String] = Map("key1" -> "{\"test\":\"test\"}")
//
//    val value1 = myMap.get("key1")
//
//    var ob: JSONObject = JSON.parseObject(value1.get)
//    var t = ob.getString("test")
//    println(t)
//
//
//    println(value1)


    val arr = Array[String]("d","fd","dg")

    println(arr.length)

    println(arr.apply(0))


  }

}
