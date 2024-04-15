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

@ExtendWith(ApprovalTest::class)
class AppTest {
    @Test
    fun test() {
        val response: Response = handler.invoke(Request(Method.GET, "/root"))

        expectThat(response) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Something happened!!")
        }

    }

    @Test
    fun testToo(approver: Approver) {
        val response: Response = handler.invoke(Request(Method.GET, "/root"))

        approver.assertApproved(response)

    }
}