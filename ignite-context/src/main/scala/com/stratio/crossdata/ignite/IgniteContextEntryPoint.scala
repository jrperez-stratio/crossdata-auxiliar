package com.stratio.crossdata.ignite

import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import com.stratio.crossdata.dataset.generator.AppArgsParser
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkConf
import org.apache.ignite.spark.IgniteContext
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import org.apache.spark.sql.ignite.IgniteSparkSession

object IgniteContextEntryPoint extends App {
  
  val arguments = new AppArgsParser(args)  
  
  val sparkConf = new SparkConf()
    .setAppName("Ignite Context XD")
    .setMaster(arguments.sparkMaster())
    .set("spark.cores.max", arguments.coresMax())
    .set("spark.driver.cores", arguments.driverCores())
    .set("spark.driver.memory", arguments.driverMemory())
    .set("spark.executor.cores", arguments.executorCores())
    .set("spark.executor.memory", arguments.executorMemory())
  //val sparkContext = SparkContext.getOrCreate(sparkConf)
  //val igniteConfig = Ignition.loadSpringBean[IgniteConfiguration]("http://10.130.19.2/ignite/client-config.xml", "ignite.cfg") 
  val igniteConfig = Ignition.loadSpringBean[IgniteConfiguration](arguments.igniteClientConfigUrl(), arguments.igniteConfigBeanName())
    .setClientMode(true)
  //Ignition.setClientMode(true)
  //Ignition.start()
  //val igniteContext = IgniteContext(sparkContext, () => igniteConfig)  
  //val cache = igniteContext.fromCache("SQL_PUBLIC_CATALOG_SALES")
  //cache.sql("select * from SQL_PUBLIC_CATALOG_SALES limit 10").show()  
  //igniteContext.ignite().memoryMetrics().foreach(u => println(s"${u.getName} ${u.getTotalAllocatedPages} ${u.getPhysicalMemoryPages}"))  
  //igniteContext.ignite().cacheNames().foreach(ch => println(s"$ch ${igniteContext.fromCache(ch).count()}"))  
  //println(igniteConfig.isClientMode())
  
  val igniteSession = IgniteSparkSession.builder().config(sparkConf)
    .igniteConfigProvider(() => igniteConfig)
    .getOrCreate()

  //igniteSession.catalog.listDatabases().show
  //igniteSession.catalog.listTables().show 

  import org.apache.ignite.spark.IgniteDataFrameSettings._
  
  timeMeasure(() => {
      val clientsDF = igniteSession.read
        .format("org.apache.ignite.spark.impl.IgniteRelationProvider")
        .option(OPTION_CONFIG_FILE, arguments.igniteClientConfigUrl())
        .option(OPTION_TABLE, "CLIENTS")
        .load().count
      println(clientsDF)
            }, "count(*) using dataframe")
  
  timeMeasure(() => {
      igniteSession.sql(arguments.countQuery()).show
            }, "count(*) using sql")
  
            
  timeMeasure(() => {
      igniteSession.sql(arguments.avgQuery()).show
            }, "avg(*) using sql")
            
  println("success -----------------")
  
  igniteSession.close()
  
  def timeMeasure(function: () => Unit, name: String) : Unit = {
    var minV = System.currentTimeMillis()
    for(i <- 0 until 3) {
      val start = System.currentTimeMillis
      function()
      val end = System.currentTimeMillis
      val timeS = (end-start)/1000
      minV = if(timeS < minV) timeS else minV  
    }
    println(s"$name takes: $minV s")
  }
  
}