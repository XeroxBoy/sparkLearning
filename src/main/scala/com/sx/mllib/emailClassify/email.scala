package com.sx.mllib.emailClassify
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
class email {

}
object email{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("classEmail").setMaster("local")
    val sc = new SparkContext(conf)
    val fileName1:String = this.getClass().getClassLoader().getResource("spam.txt").getPath();//获取文件路径
    val fileName2:String = this.getClass().getClassLoader().getResource("normal.txt").getPath();//获取文件路径

    val spam = sc.textFile(fileName1)
    val normal = sc.textFile(fileName2)
    //创建一个HashingTF实例来把邮件文本映射为包含10000个特征的向量
    val tf = new HashingTF(numFeatures = 10000)
    //各邮件都被切分为单词,每个单词映射为一个特征
    val spamFeatures = spam.map(email => tf.transform(email.split(" ")))
    val normalFeatures = normal.map(email => tf.transform(email.split(" ")))

    //创建LabelPoint数据集分别存放阳性和阴性邮件的例子
    val positiveExamples = spamFeatures.map(features=> LabeledPoint(1,features))
    val negativeExamples = normalFeatures.map(features => LabeledPoint(0,features))
    val trainingData = positiveExamples.union(negativeExamples)
    trainingData.cache()//因为逻辑回归是迭代算法，需要对RDD进行缓存

    //使用SGD算法运行逻辑回归
    val model = new LogisticRegressionWithSGD().run(trainingData)
    //以阳性和阴性例子进行测试
    val posTest = tf.transform("O M G GET cheap stuff by sending money to ...".split(" "))
    val negTest = tf.transform("Hi Dad,I started studying Spark the other ...".split(" "))
    println("prediction for positive:  "+model.predict(posTest))
    println("prediction for negative:  "+model.predict(negTest))
  }

}