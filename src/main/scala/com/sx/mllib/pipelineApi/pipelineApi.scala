package com.sx.mllib.pipelineApi

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import shapeless.Data

class pipelineApi {

}
object pipelineApi{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("classEmail").setMaster("local")
    val sc = new SparkContext(conf)
    val spark = SparkSession.builder()
      .appName("SparkSessionZipsExample")
      .getOrCreate()
    //表示文档的类 被转入schemaRDD中
    case class LabeledDocument(id:Long, text:String, label:Double)
   //获取文件路径
    val documents = sc.textFile(this.getClass().getClassLoader().getResource("spam.txt").getPath())
    val sqlContext = new SQLContext(sc)
    import spark.implicits._
    val dataSet = documents.toDS()

    //配置该机器学习流水线的三个步骤：分词，词频计数，逻辑回归；每个步骤
    //会输出SchemaRDD的一个列，并作为下一个步骤的输入列
    val tokenizer = new Tokenizer()//把邮件切分为单词
      .setInputCol("text").setOutputCol("words")
    val tf = new HashingTF() //将邮件中的单词映射为包括10000个特征的向量
      .setNumFeatures(10000).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
    val lr = new LogisticRegression()//默认使用“features”作为输入列
    val pipeline = new Pipeline().setStages(Array(tokenizer,tf,lr))
    //使用流水线对训练文档进行拟合
    val model = pipeline.fit(dataSet)
    //通过交叉验证对一批参数进行网格搜索 找到最佳模型
    val paramMaps = new ParamGridBuilder().addGrid(tf.numFeatures, Array(10000,20000)).addGrid(lr.maxIter, Array(100,2000))
      .build()//构造参数的组合
    val eval = new BinaryClassificationEvaluator()
    val cv = new CrossValidator().setEstimator(lr).setEstimatorParamMaps(paramMaps).setEvaluator(eval)
    val bestModel = cv.fit(dataSet)
  }
}