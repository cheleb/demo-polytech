package httpserver

import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*
import sttp.tapir.codec.iron.*
import sttp.tapir.codec.iron.given
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import zio.*
import zio.http.Server
import zio.json.*
import io.github.iltotore.iron.zioJson.*
import io.github.iltotore.iron.zioJson.given
import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.{*, given}
import zio.schema.validation.Predicate.Num.GreaterThan

case class User(id: Int, name: String, age: Int :| Positive)
    derives JsonCodec,
      Schema
object User:
  given Schema[IronType[Int, Positive]] =
    Schema.schemaForInt.as[IronType[Int, Positive]]

object Main extends ZIOAppDefault:

  // Domain model

  // Server implementation
  // Swagger documentation
  val swaggerEndpoint = SwaggerInterpreter().fromServerEndpoints(
    Controllers.all,
    "User API",
    "1.0.0"
  )

  // HTTP app with all routes
  val httpApp = ZioHttpInterpreter().toHttp(
    Controllers.all ++ swaggerEndpoint
  )

  // ZIO HTTP server
  val serverProgram =
    for
      port <- System.envOrElse("PORT", "8080").map(_.toInt)
      _ <- Console.printLine(s"Starting server on http://localhost:$port")
      _ <- Server.serve(httpApp) // .provide(Server.defaultWithPort(port))
    yield ()

  def run = serverProgram.provide(Server.defaultWithPort(8080))
