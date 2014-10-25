import java.io._

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.spark.sql.SQLContext

object SparkSQLExample {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SparkSQLExample").setMaster("local")

    conf.set("spark.eventLog.enabled", "true")
    conf.set("spark.eventLog.dir", "file:///home/jason/sparksqltest/myeventlog/eventlog.out")
    
    val logger = new PrintWriter(new File("logging/sparksql.out"))

    val sc = new SparkContext(conf)
    val sql = SparkQueries.importDataToRDD(sc)

    logger.write("Sending Query " + System.currentTimeMillis() + "\n")
    SparkQueries.getUserLocations(sql, sc)

    System.exit(0)
  }
}
