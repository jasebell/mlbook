import org.apache.spark.streaming.{Seconds, StreamingContext}
import StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import org.apache.spark.SparkConf

object TwitterStreaming {
  def main(args: Array[String]) {

    // Add your twitter oauth settings here, full instructions on how to
    // obtain them in Chapter 9.
    System.setProperty("twitter4j.oauth.consumerKey", "wSs8crbV3WaTFvZfSMhzXA")
    System.setProperty("twitter4j.oauth.consumerSecret", "yKKv7UqutMsvXyMuQksU1MdZeeTaa2sloTWMhADZQE")
    System.setProperty("twitter4j.oauth.accessToken", "1248789104-k5QurElGEHMabclPwLJEwCJdroWbyZaHEHeydez")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "ceqZkUPUYqv391qoKlFx6YGAe2Dx7gl5EY0xaHn9AmF5F")

    val sparkConf = new SparkConf().setAppName("TwitterStreaming")
    val streamingContext = new StreamingContext(sparkConf, Seconds(2))
    val twitterStream = TwitterUtils.createStream(streamingContext, None)

    val words = twitterStream.flatMap(status => status.getText.split(" "))

    val counts = words.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
                     .map{case (topic, count) => (count, topic)}
                     .transform(_.sortByKey(false))

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}