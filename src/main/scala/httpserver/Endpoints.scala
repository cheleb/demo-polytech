package httpserver

import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*

object Endpoints {
  // Endpoint definitions

  val createUserEndpoint =
    endpoint.post
      .in("users")
      .in(jsonBody[User])
      .out(jsonBody[User])
      .description("Create a new user")

  val getUserEndpoint =
    endpoint.get
      .in("users" / path[Int]("id"))
      .out(jsonBody[Option[User]])
      .description("Get a user by ID")

  val getAllUsersEndpoint = endpoint.get
    .in("users")
    .out(jsonBody[List[User]])
    .description("Get all users")

}
