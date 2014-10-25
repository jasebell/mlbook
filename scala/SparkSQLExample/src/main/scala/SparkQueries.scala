import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.apache.spark.sql.SQLContext

case class Customer(guid: String, 
    firstname: String, 
    lastname: String, 
    postcode: String, 
    dob: String, 
    latitude: Float, 
    longitude: Float)

//This code just copied from shark context testing 
object SparkQueries {
  
  def importDataToRDD(sc: SparkContext) : SQLContext = {
    val sqlContext = new SQLContext(sc)
    import sqlContext._
    val customers = sc.textFile("/home/jason/sparksqldemo/customers.csv").map(_.split(",")).map(c=> Customer(c(0), c(1), c(2), c(3), c(4), c(5).toFloat, c(6).toFloat)) 
    customers.registerAsTable("customers")
    cacheTable("customers")
    sqlContext
  }

  def getUserLocations(sqlContext: SQLContext, sc: SparkContext) {
    val query = "SELECT lastname, firstname, latitude, longitude FROM customers"
    sc.setJobDescription(query)
    val result = sqlContext.sql(query).collect.foreach(println)
  }
}
