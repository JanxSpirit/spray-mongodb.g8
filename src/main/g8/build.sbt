import AssemblyKeys._

import com.typesafe.startscript.StartScriptPlugin

name := "$name$"

version := "$version$"

scalaVersion := "2.9.1"

mainClass := Some("JettyLauncher")

seq(webSettings :_*)

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

port in container.Configuration := 8080

seq(assemblySettings: _*)

scalacOptions := Seq("-deprecation", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
  "se.scalablesolutions.akka" % "akka-actor" % "1.2" % "compile",
  "cc.spray" % "spray-server" % "0.8.0" % "compile",
  "cc.spray.json" %% "spray-json" % "1.0.1" % "compile",
  "org.specs2" %% "specs2" % "1.6.1"  % "test",
  "org.eclipse.jetty" % "jetty-webapp" % "8.0.3.v20111011" % "container, compile",
  "org.eclipse.jetty" % "jetty-server" % "8.0.3.v20111011" % "container, compile",
  "org.eclipse.jetty" % "jetty-util" % "8.0.3.v20111011" % "container, compile",
  "se.scalablesolutions.akka" % "akka-slf4j" % "1.2",
  "org.slf4j" % "slf4j-api" % "1.6.1",
  "ch.qos.logback" % "logback-classic" % "0.9.29"
  )

resolvers ++= Seq(
  "Akka Repository" at "http://repo.akka.io/releases/",
  "Web plugin repo" at "http://siasia.github.com/maven2",
  ScalaToolsSnapshots
)



