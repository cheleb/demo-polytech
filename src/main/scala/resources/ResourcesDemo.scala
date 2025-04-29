package resources

import zio.*

object ResourcesDemo extends ZIOAppDefault {

  var fragile =
    new AutoCloseable() {

      println("Creating resource")

      override def toString: String = "Resource($name)"
      override def close(): Unit = println("Closing resource $name")
    }
  def run = {
    fragile
    Console.printLine("Hello, ZIO!")
  }
}
