package com.yugo

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

object KafkaComsumer {

  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setMaster("local[2]").setAppName("UserClickCountStat")
    val scc = new StreamingContext(conf, Seconds(5))

    val brokers = "192.168.127.161:9092,192.168.127.162:9092,192.168.127.163:9092"

    val kafkaParam = Map[String, String](
      "bootstrap.servers" -> brokers
      , "serializer.class" -> "kafka.serializer.StringEncoder"
      //      , "auto.offset.reset" -> "smallest"
      , "group.id" -> "test")

    val topic = Set[String]("test_flume")

    val kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](scc, kafkaParam, topic)


    val events = kafkaStream.flatMap(line => {
      line._2.split(" ")
      Some(line._1,line._2)

    })

    //    val events = kafkaStream.flatMap(line => {
    ////      val data = JSONObject.fromObject(line._2)
    //      Some(line)
    //    })

    events.print()
    scc.start()
    scc.awaitTermination()

  }


}
