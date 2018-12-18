package com.yuguo

import java.io.File

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SqlTest {


  def main(args: Array[String]): Unit = {

    val warehouseLocation = new File("spark-warehouse").getAbsolutePath

    val spark = SparkSession
      .builder()
      .appName("Spark Hive Example")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()


    import spark.implicits._
    import spark.sql

    val tableName = "src"
    println("**********")

    sql("use default")
    val tables = sql("show tables")


    tables.show()

    var isExit = false;
    tables.collect().foreach(t => {
      if (t.getAs[String]("tableName").equals(tableName)) {
        isExit = true
      }
    })

    println("isExit"+isExit)
    val src1 = sql("select * from src1")
    src1.createTempView("temp")
    if (isExit) {
      sql("insert into src select key,value from temp")
    } else {
      sql("CREATE TABLE IF NOT EXISTS src as select * from temp")
    }



    //    tables.foreach(row => {
    //      println("+++++++")
    //      println(row.size)
    //    })

    //    sql("CREATE TABLE IF NOT EXISTS src (key INT, value STRING) USING hive")
    //    sql("insert into src select key,value from src1")
    //    sql("LOAD DATA LOCAL INPATH 'examples/src/main/resources/kv1.txt' INTO TABLE src")

    // Queries are expressed in HiveQL
    //    sql("SELECT * FROM src").show()

    spark.stop()

  }

}
