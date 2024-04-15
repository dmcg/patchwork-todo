import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class AppTest {
    @Test
    fun test() {
        val response: Response = handler.invoke(Request(Method.GET, "/root"))

        expectThat(response) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Something happened")
        }

    }

    @Test
    fun testToo() {
        val response: Response = handler.invoke(Request(Method.GET, "/root"))

        expectThat(response) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Something happened")
        }

    }
}