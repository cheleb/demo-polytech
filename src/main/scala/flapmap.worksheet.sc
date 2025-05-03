val oo = "11"

val maybeOne = Option(1)
def maybeTwo = Option(2) //.empty[Int]
val maybeThree = Option(3)

val res = for {
  one <- maybeOne
  two <- maybeTwo
  three <- maybeThree
} yield one + two + three

def foo(i: Int): Unit = println(i + i)
val foo2: Int => Unit = i => println(i)

def fooLazy(i: => Int): Unit = println(i + i)

def toto = {
  println("toto")
  1
}

foo(toto)

fooLazy(toto)

// ETA expansion

//res.foreach(fooLazy(_))

type Thunk[+A] = () => A

case class IO[+A](value: Thunk[A]) {

  def map[B](f: A => B): IO[B] = IO(() => f(value()))

  def flatMap[B](f: A => IO[B]): IO[B] = IO(() => f(value()).value())
}

object IO {
  def succeed[A](a: => A): IO[A] = IO(() => a)
}

val res2 = for {
  i <- IO.succeed(10)
  str <- IO.succeed(s"Hello, world! $i")
} yield str

res2.value() // "Hello, world! 10"
