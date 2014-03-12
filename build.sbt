name := "webapp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "3.0.6",
  cache
)     

play.Project.playScalaSettings
