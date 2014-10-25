import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object ScalaMRExample {
  def main(args: Array[String]) {
    val configuration = new SparkConf().setAppName("Scala MapReduce Example")
    val textFile = "/home/jason/worldbrain.txt" 
    val sc = new SparkContext(configuration)    
    val textSc = sc.textFile(textFile)
    val mapred = textSc.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey((a,b) => a+b)

    mapred.collect

    mapred.saveAsTextFile("/home/jason/wboutput")

  }
}
