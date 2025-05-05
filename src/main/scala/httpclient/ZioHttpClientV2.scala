package httpclient

import sttp.tapir.client.sttp4.stream.StreamSttpClientInterpreter
import httpserver.Endpoints
import sttp.client4.httpclient.zio.HttpClientZioBackend
import sttp.tapir.client.sttp4.SttpClientInterpreter
import zio.*
import sttp.client4.*
import sttp.model.*
import sttp.tapir.*
import zio.logging.backend.SLF4J
import httpserver.User

object SimpleZIOHttpClientV2 extends ZIOAppDefault {

  // Domain model

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  val httpClientZioBackend =
    HttpClientZioBackend()

  def showUser(
      client: Int => ZIO[Any, Throwable, Option[User]]
  )(id: Int): ZIO[Any, Throwable, Option[User]] =
    for {
      user <- client(id)
      _ <- ZIO.logInfo(s"User: $user")
    } yield user

  val program: ZIO[Any, Throwable, Unit] = for {
    backend <- HttpClientZioBackend()
    client = SttpClientInterpreter()
      .toClientThrowErrors(
        Endpoints.getUserEndpoint,
        Some(uri"http://localhost:8080"),
        backend
      )
    _ <- ZIO.foreach(List(1, 2, 3, 4))(showUser(client))
    _ <- ZIO.logInfo("Finished processing users.")

  } yield ()

  def run = program

}
