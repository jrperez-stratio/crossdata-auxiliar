package com.stratio.crossdata.dataset.generator

import org.apache.spark.sql.Column
import scala.util.Random
import scala.collection.mutable.HashSet
import java.security.SecureRandom

object GeneratorUtils {

  val random = new SecureRandom()
  
  def getRandomValue[T](columns: Seq[T]) : T = columns(random.nextInt(columns.length))
  
  def nextRandom(map: HashSet[Int]) : Int = {
    var value = 1;
    while(map.contains(value)) value = random.nextInt(100000000)
    map.add(value)
    value
  }
  
}