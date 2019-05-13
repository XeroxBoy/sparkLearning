package com.sx.mllib.vector
import org.apache.spark.mllib.linalg.Vectors
object VectorTest {
  def main(args: Array[String]): Unit = {
    //稠密向量 接受一串值或一个数组
    val denseVec1 = Vectors.dense(1.0,2.0,3.0)
    val denseVec2 = Vectors.dense(Array(1.0,2.0,3.0))
    //稀疏向量,系数分别为向量维度，非零位位置，对应值
    val sparseVec1 = Vectors.sparse(4,Array(0,2),Array(1.0,2.0))
  }
}
