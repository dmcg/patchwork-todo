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

    private val items = Todos(listOf("item1", "item2", "item3"))

    @Test
    fun testToo(approver: Approver) {
        val response: Response = items.routes.invoke(Request(Method.GET, "/"))
        approver.assertApproved(response)
    }

    @Test
    fun testTodoItems() {
        val items = items
        val response: Response = items.routes(Request(Method.GET, "/items"))

        expectThat(response) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo(items.joinToString())
        }
    }
}