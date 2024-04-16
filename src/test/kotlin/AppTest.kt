import org.http4k.core.*
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.http4k.testing.ApprovalTest
import org.http4k.testing.Approver
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.doesNotContain
import strikt.assertions.isEqualTo
import strikt.assertions.size

@ExtendWith(ApprovalTest::class)
class AppTest {

    private val items = Todos(listOf("Learn Kotlin", "Buy milk", "Buy Oreos"))

    @Test
    fun testToo(approver: Approver) {
        val response: Response = items.routes.invoke(Request(Method.GET, "/"))
        approver.assertApproved(response)
    }

    @Test
    fun testTodoItems() {
        val response: Response = items.routes(Request(Method.GET, "/items"))

        expectThat(response) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Learn Kotlin\nBuy milk\nBuy Oreos")
        }
    }

    @Test
    fun deleteItemFromToDo() {
        val second = items[1]
        val response = items.routes(Request(Method.DELETE, UriTemplate.from("/item/{id}")).with(idLens of second.id.toString()))
        expectThat(response) {
            status.isEqualTo(Status.OK)
        }
        expectThat(items) {
            size.isEqualTo(2)
            doesNotContain(second)
        }
    }
}