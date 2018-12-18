package com.yuguo

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils


object KafkaTest {


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[2]").setAppName("UserClickCountStat")
    val scc = new StreamingContext(conf, Seconds(5))

    val brokers = "172.168.8.132:9092,172.168.8.133:9092,172.168.8.134:9092"

    val kafkaParam = Map[String, String]("bootstrap.servers" -> brokers
      , "serializer.class" -> "kafka.serializer.StringEncoder"
    ,"auto.offset.reset" -> "smallest")

    val topic = Set[String]("heartbeat_log")

    val kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](scc, kafkaParam, topic)


    val events = kafkaStream.flatMap(line => {
      println(line)
      Some(line)
    })

    //    val events = kafkaStream.flatMap(line => {
    ////      val data = JSONObject.fromObject(line._2)
    //      Some(line)
    //    })

//    events.print()
    scc.start()
    scc.awaitTermination()

  }


}
