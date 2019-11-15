package com.stratio.crossdata.dataset.generator

object Constant {
  
  val upperCaseLetters = for(idx <- 0 until 26) yield (('A' + idx).toString())
  
  /*val clients_file = "/finegrained/clients"
  val contracts_file = "/finegrained/contracts"*/
  
  def file_path(name: String)(implicit appArgsParser: AppArgsParser) = s"${appArgsParser.basePath()}/$name"
  
  
}