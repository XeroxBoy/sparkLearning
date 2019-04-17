package com.sx
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
class Main{

}
object Main{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlsc = new SQLContext(sc)
    val fileName:String = this.getClass().getClassLoader().getResource("employee.json").getPath();//获取文件路径
    val dfs = sqlsc.read.json(fileName)
//    val dfs = sqlsc.read.json("/employee.json")
//    dfs.show()
//    dfs.show()
//    dfs.printSchema()
//    dfs.select("name").show()
//    dfs.filter(dfs("age")>23).show()
//    dfs.groupBy("age").count().show()
    dfs.write.parquet("employee.parquet")
    val parqfile = sqlsc.read.parquet("employee.parquet")
    parqfile.registerTempTable("employee")
    val allRecords = sqlsc.sql("SELECT * FROM employee")
    allRecords.show()
  }
}