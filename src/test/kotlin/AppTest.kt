import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.http4k.testing.ApprovalTest
import org.http4k.testing.Approver
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo

@ExtendWith(ApprovalTest::class)
class AppTest {
    @Test
    fun test() {
        val response: Response = routes.invoke(Request(Method.GET, "/"))

        expectThat(response) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Something happened!!")
        }

    }

    @Test
    fun testToo(approver: Approver) {
        val response: Response = routes.invoke(Request(Method.GET, "/"))

        approver.assertApproved(response)

    }

    @Test
    fun testTodoItems() {
        val response: Response = routes.invoke(Request(Method.GET, "/items"))

        expectThat(response) {
            status.isEqualTo(Status.OK)
            bodyString.isNotEqualTo("Something happened!!")
        }
    }
}