package com.stratio.crossdata.alluxio

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkConf
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import org.apache.spark.sql.SparkSession
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path

object kerberizedHDFSAccess extends App {

  val conf = new Configuration
  conf.set("fs.defaultFS", "hdfs://hdfs01.poole.hetzner.stratio.com:8020")
  conf.set("hadoop.security.authentication", "kerberos")
  
  UserGroupInformation.setConfiguration(conf)
  /*UserGroupInformation.loginUserFromKeytab("hdfs/hdfs01.poole.hetzner.stratio.com@STRATIO.COM",
            "/home/jrperez/STRATIO.COM.keytab")*/
  UserGroupInformation.loginUserFromKeytab("hdfs/hdfs01.poole.hetzner.stratio.com@STRATIO.COM",
            "/home/jrperez/STRATIO.COM.keytab")
  val fs = FileSystem.get(conf)          
            
  //val fileStatuses = fs.listStatus(new Path("/alluxio/"));
  val fileStatuses = fs.listStatus(new Path("/"));
  for (fileStatus <- fileStatuses) {
      System.out.println(fileStatus.getPath().getName());
  }
           
            
}