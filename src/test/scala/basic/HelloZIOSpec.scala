package basic

import zio.test.ZIOSpecDefault
import zio.test.*
import zio.test.Assertion.*

object HelloZIOSpec extends ZIOSpecDefault {
  def spec = suite("Hello ZIO")(
    test("Hello ZIO") {
      assert(1)(equalTo(1))
    },
    test("test")(
      check(Gen.int) { i =>
        println(i)
        assert(math.abs(i))(isGreaterThan(0))
      }
    )
  )
}
