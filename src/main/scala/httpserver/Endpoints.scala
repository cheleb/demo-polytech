package httpserver

import sttp.tapir.*
import sttp.tapir.generic.auto.*

import sttp.tapir.json.zio.*
object Endpoints {
  // Endpoint definitions
  val getUserEndpoint: Endpoint[Unit, Int, Unit, Option[User], Any] =
    endpoint.get
      .in("users" / path[Int]("id"))
      .out(jsonBody[Option[User]])
      .description("Get a user by ID")

  val getAllUsersEndpoint = endpoint.get
    .in("users")
    .out(jsonBody[List[User]])
    .description("Get all users")

}
