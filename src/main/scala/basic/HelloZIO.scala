package basic

import zio.*
import java.io.IOException

object HelloApp extends App {

  def helloWorld: ZIO[Any, IOException, Unit] = Console.printLine("Hello, ZIO!")

  Unsafe.unsafe { implicit unsafe =>
    Runtime.default.unsafe.fork(
      helloWorld
    )
  }
}

object HelloZIOApp extends ZIOAppDefault:

  def run = Console.printLine("Hello, ZIO!")
