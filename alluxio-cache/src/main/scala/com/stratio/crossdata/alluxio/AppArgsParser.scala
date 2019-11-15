package com.stratio.crossdata.alluxio

import org.rogach.scallop.{ScallopConf, singleArgConverter}

class AppArgsParser(arguments: Seq[String]) extends ScallopConf(arguments) {
  val sparkMaster = opt[String] (required = true)
  val format = opt[String] (required = true)
  val path = opt[String] (required = true)
  
  
  val executorCores = opt[String](default = Some("2"))
  val coresMax = opt[String](default = Some("4"))
  val executorMemory = opt[String](default = Some("2g"))
  val driverCores = opt[String](default = Some("2"))
  val driverMemory = opt[String](default = Some("2g"))
    
  verify()
  
}