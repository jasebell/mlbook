import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors

val inputData = sc.textFile("data/kmeans_data.txt")
val parsedData = inputData.map(s => Vectors.dense(s.split(' ').map(_.toDouble)))

val numClusters = 2
val numIterations = 20
val clusters = KMeans.train(parsedData, numClusters, numIterations)


val WSSSE = clusters.computeCost(parsedData)
println("WSSSE = " + WSSSE)