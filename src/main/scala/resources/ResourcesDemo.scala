package resources

import zio.*

object ResourcesDemo extends ZIOAppDefault {

  def fragile(name: String) = ZIO.fromAutoCloseable(
    ZIO.succeed(
      new AutoCloseable() {

        println("Creating resource")

        override def toString: String = s"Resource($name)"
        override def close(): Unit = println(s"Closing resource $name")
      }
    )
  )

  def program(app: String): ZIO[Any, Nothing, Unit] = ZIO.scoped(for {
    resourceA <- fragile(s" $app A")
    resourceB <- fragile(s" $app B")
    resourceB <- fragile(s" $app B")
  } yield ())

  def run = {
    program("Foo").race(ZIO.sleep(1.second) *> program("Bar")) *>
      Console.printLine("Hello, ZIO!")
  }
}
