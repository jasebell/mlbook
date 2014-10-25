name := "TwitterStreaming"

version := "1.0"

scalaVersion := "2.10.4"

unmanagedBase := baseDirectory.value / "pluginlib"

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.8.7")

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.0.0"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.0.0"

libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.0.0"

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "3.0.3"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

