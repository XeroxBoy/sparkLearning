package com.sx

import org.apache.spark.{SparkConf, SparkContext}

class sparkTest00 {

}
object sparkTest00{
  def main(args: Array[String]): Unit = {
        val sparkConf = new SparkConf().setAppName("pair rdd").setMaster("local")
        val sc = new SparkContext(sparkConf)
        val lines = sc.parallelize(List("scala ide","java ide","python ide","scala ide"))
        val pairs = lines.map(x =>(x.split(" ")(0),1)).reduceByKey((x,y)=>x+y)
        for (pair <- pairs){
          println(pair)
        }

  }
}