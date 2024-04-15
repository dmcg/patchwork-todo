import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.*


class TodosTests {
    private val items = Todos(listOf("Learn Kotlin", "Buy milk", "Buy Oreos"))

    @Test
    fun `deleteItemById ignores missing items`() {
        val notThere = UUID.randomUUID()
        items.deleteItemById(notThere)
        expectThat(items.size).isEqualTo(3)
    }
}