package com.stratio.crossdata.dataset.generator

import org.rogach.scallop.{ScallopConf, singleArgConverter}

class AppArgsParser(arguments: Seq[String]) extends ScallopConf(arguments) {
  val sparkMaster = opt[String] (required = true)
  val basePath = opt[String](required = true)    
  val format = opt[String](default = Some("parquet"))
  val rowLoops = opt[Int](required = true)
  val loops = opt[Int](required = true)

  verify()
}