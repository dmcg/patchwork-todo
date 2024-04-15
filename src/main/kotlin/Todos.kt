import java.util.UUID

class Todos(
    items: List<TodoItem>
) : List<TodoItem> by items {
    fun deleteItemById(uuid: UUID) {

    }

}

fun Todos(items: List<String>) = Todos(
    items.map { name -> TodoItem(UUID.randomUUID(), name) }
)

data class TodoItem(
    val id: UUID,
    val name: String,
)

