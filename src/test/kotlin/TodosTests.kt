import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.doesNotContain
import strikt.assertions.isEqualTo
import strikt.assertions.size
import java.util.*


class TodosTests {
    private val items = Todos(listOf("Learn Kotlin", "Buy milk", "Buy Oreos"))

    @Test
    fun `deleteItemById ignores missing items`() {
        val notThere = UUID.randomUUID()
        items.deleteItemById(notThere)
        expectThat(items.size).isEqualTo(3)
    }

    @Test
    fun `deleteItemById deletes`() {
        val second = items[1]
        items.deleteItemById(second.id)
        expectThat(items) {
            size.isEqualTo(2)
            doesNotContain(second)
        }
    }
}