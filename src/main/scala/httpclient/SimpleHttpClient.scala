package httpclient

import sttp.tapir.client.sttp4.stream.StreamSttpClientInterpreter
import httpserver.Endpoints
import sttp.client4.httpclient.zio.HttpClientZioBackend
import sttp.client4.quick.*
import sttp.tapir.client.sttp4.SttpClientInterpreter
import sttp.tapir.DecodeResult.Value
import sttp.tapir.DecodeResult.Missing
import sttp.tapir.DecodeResult.Multiple
import sttp.tapir.DecodeResult.Mismatch
import sttp.tapir.DecodeResult.InvalidValue

object SimpleHttpClient extends App {
  import sttp.client4.*
  import sttp.model.*
  import sttp.tapir.*

  import sttp.tapir.json.zio.*
  import zio.*
  import zio.json.*

  // Domain model
  case class User(id: Int, name: String) derives JsonCodec

  // Client implementation

  val client = SttpClientInterpreter()
    .toClient(
      Endpoints.getUserEndpoint,
      Some(uri"http://localhost:8080"),
      backend
    )

  client(1) match
    case Value(v) =>
      println(s"User: $v")
    case Missing                                        =>
    case Multiple(vs)                                   =>
    case sttp.tapir.DecodeResult.Error(original, error) =>
    case Mismatch(expected, actual)                     =>
    case InvalidValue(errors)                           =>

}
