package com.sx.mllib.regression

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}

object linear {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("classEmail").setMaster("local")
    val sc = new SparkContext(conf)
    val fileName1:String = this.getClass().getClassLoader().getResource("spam.txt").getPath();//获取文件路径

    val spam = sc.textFile(fileName1)
    //创建一个HashingTF实例来把邮件文本映射为包含10000个特征的向量
    val tf = new HashingTF(numFeatures = 10000)
    //各邮件都被切分为单词,每个单词映射为一个特征
    val spamFeatures = spam.map(email => tf.transform(email.split(" ")))

    //创建LabelPoint数据集分别存放阳性和阴性邮件的例子
    val positiveExamples = spamFeatures.map(features=> LabeledPoint(1,features))
    val lr = new LinearRegressionWithSGD()
    positiveExamples.cache()
    val model = lr.run(positiveExamples)
    println("weight:%s,  intercept:%s".format(model.weights,model.intercept))
    val posTest = tf.transform("O M G GET cheap stuff by sending money to ...".split(" "))
    println(model.predict(posTest))
  }
}
