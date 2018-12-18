package com.yuguo

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.sql.{SQLContext, SparkSession}

object SparkSqlTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("DfDemo").master("local[2]").getOrCreate()
    import org.apache.spark.sql.types._
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]

    var da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())

    val data = Array(("1", "2", "3", "4", da),("1", "3", "3", "4", da) ,("6", "7", "8", "9", da))

    ////    val filed = Array("col1", "col2", "col3", "col4", "col5")
    //    val df = spark.createDataFrame(data).toDF("col1", "col2", "col3", "col4", "col5")
    //
    //     df.map(row => {
    //      row.getValuesMap[Any](List("col1", "col2"))
    //
    //
    //
    //    }).show(true)


    val sql = spark.sqlContext
    val df = spark.createDataFrame(data).toDF("col1", "col2", "col3", "col4", "col5")
//    df.createOrReplaceTempView("test")
//
//    val d = sql.sql("select *,date_format(col5,'yyyy-MM-dd HH:00:00') col5_ from test ")
//    d.show(false)
//    d.createOrReplaceTempView("test1")
//
//    sql.sql("select col5_,count(col1) from test1 group by col5_").show()
//
//    df.show(false)

    val agg_ = Map("col2"->"sum")

    import org.apache.spark.sql.functions._

    df.groupBy("col1").agg(avg("col2").alias("tt")).show(false)


    spark.stop()

  }

}
