val scala3Version = "3.6.4"
val tapirVersion = "1.11.25"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Demo Polytech",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.17",
      "dev.zio" %% "zio-streams" % "2.1.17",
      // Tapir dependencies
      "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-zio" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-client4" % tapirVersion,

      // Iron dependencies
      "com.softwaremill.sttp.tapir" %% "tapir-iron" % tapirVersion,
      "io.github.iltotore" %% "iron-zio-json" % "3.0.1",

      // STTP dependencies
      "com.softwaremill.sttp.client4" %% "core" % "4.0.2",
      "com.softwaremill.sttp.client4" %% "zio" % "4.0.2",

      // Logging dependencies
      "ch.qos.logback" % "logback-classic" % "1.5.18",
      "dev.zio" %% "zio-logging-slf4j2" % "2.5.0",
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "dev.zio" %% "zio-test" % "2.1.17" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.1.17" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
