package resources

import zio.*

object ResourcesDemo extends ZIOAppDefault {

  def fragile(name: String) =
    ZIO.fromAutoCloseable(ZIO.succeed(new AutoCloseable() {

      override def toString: String = s"Resource($name)"
      override def close(): Unit = println(s"Closing resource $name")
    }))

  def program = ZIO.scoped(
    for {
      resource1 <- fragile("A")
      resource2 <- fragile("B")
      resource3 <- fragile("C")
      _ <- ZIO.succeed(println(s"Using resource $resource1"))
      _ <- ZIO.fail("Failing")
      _ <- ZIO.succeed(println("Using resource"))

    } yield ()
  )

  def program2 =
    for {
      resource1 <- fragile("A2")
      resource2 <- fragile("B2")
      resource3 <- fragile("C2")
      _ <- ZIO.succeed(println(s"Using resource $resource1"))
      _ <- ZIO.fail("Failing")
      _ <- ZIO.succeed(println("Using resource"))

    } yield ()

  def run = program.ignore *> program2
}
