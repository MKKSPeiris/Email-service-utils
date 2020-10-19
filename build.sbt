
name := "EmailLoadTest-service"

description := "This is the rest api for email service"

version := "0.1"

scalaVersion := "2.11.12"

enablePlugins(ServicePlugin)


libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-http" % "10.1.5",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.5",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5",
    "org.scalatest" %% "scalatest" % "3.1.0" % Test,
    "com.pagero" % "email-service-spec" % "1.55.1-GO" % "proto",
    "com.pagero" % "auth-spec" % "1.48.1-GO" % "proto",
    "org.apache.httpcomponents" % "httpclient" % "4.5.7",
    "commons-io" % "commons-io" % "2.5",
    "org.apache.commons" % "commons-text" % "1.9"

  )
}