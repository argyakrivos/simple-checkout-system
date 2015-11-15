val akkaV = "2.4.0"
val sprayV = "1.3.3"
val json4sV = "3.3.0"

lazy val root = (project in file(".")).
  settings(
    name := "checkout-service",
    version := "1.0.0",
    organization := "me.akrivos",
    scalaVersion := "2.11.7",
    scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Xfatal-warnings", "-feature", "-Xfuture"),

    libraryDependencies ++=
      "com.typesafe.akka" %% "akka-actor"     % akkaV ::
      "com.typesafe.akka" %% "akka-slf4j"     % akkaV ::
      "io.spray"          %% "spray-can"      % sprayV ::
      "io.spray"          %% "spray-routing"  % sprayV ::
      "org.json4s"        %% "json4s-jackson" % json4sV ::
      Nil,

    libraryDependencies ++=
      "io.spray"          %% "spray-testkit"  % sprayV  % Test ::
      "org.scalatest"     %% "scalatest"      % "2.2.5" % Test ::
      Nil
  )