import java.util.UUID

class Todos(
    private val items: MutableList<TodoItem>
) : List<TodoItem> by items {

    fun deleteItemById(uuid: UUID) {
        items.removeIf { it.id == uuid }
    }
}

fun Todos(items: List<String>) = Todos(
    items.map { name -> TodoItem(UUID.randomUUID(), name) }.toMutableList()
)

data class TodoItem(
    val id: UUID,
    val name: String,
)

