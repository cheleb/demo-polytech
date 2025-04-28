package exercices

import zio.*
import zio.Console.*
import zio.Random._

object NumberGuessing extends ZIOAppDefault {

  private lazy val readInt = for {
    line <- readLine
    int <- ZIO.attempt(line.toInt)
  } yield int

  private lazy val readIntAndRetry: URIO[Any, Int] =
    readInt
      .orElse(
        printLineError("Not a valid integer...").orDie
          *> readIntAndRetry
      )

  private def makeAGuess(secret: Int): ZIO[Any, Throwable, Int] =
    for {
      guess <-
        readIntAndRetry // .flatMap(str => ZIO.fromTry(Try(str.toInt))).retry(Schedule.forever)
      _ <-
        if (guess < secret)
          printLine("Too low") *> makeAGuess(secret)
        else if (guess > secret)
          printLine("To High") *> makeAGuess(secret)
        else
          printLine("Won !")
    } yield guess

  private val program: ZIO[Any, Throwable, Unit] = for {
    secret <- nextIntBounded(100)
    _ <- printLine("Guess a number?")
    res <- makeAGuess(secret) // .timeout(5.seconds)

  } yield ()

  override def run = program // .timeout(2.second)
}
