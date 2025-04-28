val scala3Version = "3.7.0-RC4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Demo Polytech",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.17",
      "dev.zio" %% "zio-streams" % "2.1.17",
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "dev.zio" %% "zio-test" % "2.1.17" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.1.17" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
