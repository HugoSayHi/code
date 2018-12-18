package com.yuguo

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ReduceTest {


  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.INFO)

    System.setProperty("hadoop.home.dir", "C:\\Program Files\\hadoop")

    val conf = new SparkConf().setMaster("local[2]").setAppName("network_test")

    val scc = new StreamingContext(conf, Seconds(10))

    scc.checkpoint("\\temp\\checkpoint")

    val lines = scc.socketTextStream("192.168.127.170", 9999)

    val words = lines.flatMap(_.split(" ")).map(w => (w, 1))


    val addFunc = (currValue: Seq[Int], prepValue: Option[Int]) => {
      var sum = currValue.sum
      var i = prepValue.getOrElse(0)
      Some(sum + i)
    }

    words.updateStateByKey(addFunc, 1).print()


//    val re = words.reduceByKeyAndWindow((a: Int, b: Int) => {
//      a + b
//    }, Seconds(60), Seconds(10))
//
//    re.print()

    //    val win = words.window(Seconds(3), Seconds(1))
    //    win.print()

    scc.start()
    scc.awaitTermination()


  }

}
