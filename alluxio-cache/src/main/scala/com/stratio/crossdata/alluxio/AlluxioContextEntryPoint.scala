package com.stratio.crossdata.alluxio

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkConf
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import org.apache.spark.sql.SparkSession
import java.net.URL
import java.nio.file.Paths
import org.apache.spark.sql.DataFrame

object AlluxioContextEntryPoint extends App {
  
  val arguments = new AppArgsParser(args)  
  System.setProperty("alluxio.security.login.impersonation.username", "operador")
  val sparkConf = new SparkConf()
    .setAppName("Alluxio Context XD")
    .setMaster(arguments.sparkMaster())
    .set("spark.cores.max", arguments.coresMax())
    .set("spark.driver.cores", arguments.driverCores())
    .set("spark.driver.memory", arguments.driverMemory())
    .set("spark.executor.cores", arguments.executorCores())
    .set("spark.executor.memory", arguments.executorMemory())

    
  val sparkContext = SparkContext.getOrCreate(sparkConf)
  
  implicit val sparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
    
  readAndCount(arguments.format(), arguments.sparkMaster() match {
     case "local[*]" => absolutePath(arguments.path())
     case _ => arguments.path()
    })
 
  def readAndCount(format: String, path: String)(implicit sparkSession: SparkSession) {
    format match {
      case "csv" => printCount(readCSV(path))
      case "parquet" => printCount(readParquet(path))
      case _ => println("Invalid file format")
    }
  }
  
  def readCSV(path: String)(implicit sparkSession: SparkSession) = sparkSession.read.option("header", "true").csv(path)
  def readParquet(path: String)(implicit sparkSession: SparkSession) = sparkSession.read.parquet(path)
  def printCount(dataFrame: DataFrame) = println(s"data set size ${"="*40} ${dataFrame.count()}")
  
    
  def absolutePath(resourceRelative: String): String = {
    val res = getClass().getClassLoader().getResource(resourceRelative)
    val file = Paths.get(res.toURI()).toFile()
    file.getAbsolutePath()
  }
  
}