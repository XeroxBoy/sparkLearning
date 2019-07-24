package com.sx

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

class sparkTest00 {

}
object sparkTest00{
  def main(args: Array[String]): Unit = {
//        val sparkConf = new SparkConf().setAppName("pair rdd").setMaster("local")
//        val sc = new SparkContext(sparkConf)
//        val lines = sc.parallelize(List("scala ide","java ide","python ide","scala ide"))
//        val pairs = lines.map(x =>(x.split(" ")(0),1)).reduceByKey((x,y)=>x+y)
//        for (pair <- pairs){
//          println(pair)
//        }
        pageRank()
  }
  def pageRank(): Unit ={
    val sparkConf = new SparkConf().setAppName("page Rank").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val links = sc.objectFile[(String,Seq[String])]("links").partitionBy(new HashPartitioner(100))
      .persist()
    var ranks = links.mapValues(v => 1.0)
    for (i <- 0 until 10){
      val contributions = links.join(ranks).flatMap {
        case (pageId,(links,rank)) =>
          links.map(dest => (dest, rank/links.size))
      }
      ranks = contributions.reduceByKey((x,y) => x+y).mapValues(v => 0.15+0.85*v)
    }
    ranks.saveAsTextFile("ranks")
  }
}