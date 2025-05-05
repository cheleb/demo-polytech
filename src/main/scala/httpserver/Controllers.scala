package httpserver

import zio.*
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir.*
import sttp.tapir.server.ServerEndpoint

import io.github.iltotore.iron.*

// Sample data
var users = List(
  User(1, "John Doe", 30),
  User(2, "Jane Smith", 25),
  User(3, "Bob Johnson", 40)
)

object Controllers {

  val createUserRoute: ServerEndpoint[Any, Task] =
    Endpoints.createUserEndpoint.zServerLogic { user =>
      users = users :+ user
      ZIO.succeed(user)
    }

  val getUserRoute: ServerEndpoint[Any, Task] =
    Endpoints.getUserEndpoint.zServerLogic { id =>
      ZIO.succeed(users.find(_.id == id))
    }

  val getAllUsersRoute: ServerEndpoint[Any, Task] =
    Endpoints.getAllUsersEndpoint.zServerLogic(_ => ZIO.succeed(users))

  def getUserById(id: Int): Task[Option[User]] =
    ZIO.succeed(users.find(_.id == id))

  val all: List[ServerEndpoint[Any, Task]] = List(
    createUserRoute,
    getUserRoute,
    getAllUsersRoute
  )
}
