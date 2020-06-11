name := "Agregator"

version := "0.1"

scalaVersion := "2.13.2"

val ScalaTestVersion = "3.1.2"
val AkkaVersion = "2.6.5"
val AkkaHttpVersion = "10.1.11"
val AlpakkaVersion = "2.0.0"
val ScalaScraperVersion = "2.2.0"
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % AlpakkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "net.ruippeixotog" %% "scala-scraper" % ScalaScraperVersion,

  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)