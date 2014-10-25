import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.storage.StorageLevel

object TCPIngester {
  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("TCPIngester")
    val ssc = new StreamingContext(sparkConf, Seconds(1))

    
    val incomingLines = ssc.socketTextStream("127.0.0.1", 10000, StorageLevel.MEMORY_AND_DISK_SER)
    val words = incomingLines.flatMap(_.split(" "))
    val counts = words.map(w => (w, 1)).reduceByKey(_ + _)
    counts.print

    counts.saveAsTextFiles("/home/jason/streamtestout/mystream_",".txt")
    
    ssc.start()
    ssc.awaitTermination()
  }
}

