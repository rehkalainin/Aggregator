
name := "aggregator"
organization := "rehkalainin"

version := "0.1.0"

scalaVersion := "2.13.2"

val scalaTestVersion = "3.1.2"
val akkaVersion = "2.6.5"
val akkaHttpVersion = "10.1.11"
val alpakkaVersion = "2.0.0"
val scalaScraperVersion = "2.2.0"
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % alpakkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "net.ruippeixotog" %% "scala-scraper" % scalaScraperVersion,

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",

)


