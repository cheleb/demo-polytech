package httpserver

import zio.*
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir.*
import sttp.tapir.server.ServerEndpoint

// Sample data
val users = List(
  User(1, "John Doe"),
  User(2, "Jane Smith"),
  User(3, "Bob Johnson")
)

object Controllers {
  val getUserRoute: ServerEndpoint[Any, Task] =
    Endpoints.getUserEndpoint.zServerLogic { id =>
      ZIO.succeed(users.find(_.id == id))
    }

  val getAllUsersRoute: ServerEndpoint[Any, Task] =
    Endpoints.getAllUsersEndpoint.zServerLogic(_ => ZIO.succeed(users))

  def getUserById(id: Int): Task[Option[User]] =
    ZIO.succeed(users.find(_.id == id))

  val all: List[ServerEndpoint[Any, Task]] = List(
    getUserRoute,
    getAllUsersRoute
  )
}
