package com.stratio.crossdata.dataset.generator

import org.rogach.scallop.{ScallopConf, singleArgConverter}

class AppArgsParser(arguments: Seq[String]) extends ScallopConf(arguments) {
  val sparkMaster = opt[String] (required = true)
  val igniteClientConfigUrl = opt[String] (required = true)
  val igniteConfigBeanName = opt[String] (required = true)
  
  val executorCores = opt[String](default = Some("4"))
  val coresMax = opt[String](default = Some("8"))
  val executorMemory = opt[String](default = Some("4g"))
  val driverCores = opt[String](default = Some("2"))
  val driverMemory = opt[String](default = Some("4g"))
  
  val countQuery = opt[String](default = Some("select count(*) from CLIENTS"))
  val avgQuery = opt[String](default = Some("select avg(TURNOVER_BILLING_AMOUNT) from CLIENTS"))
  
  verify()
  
}