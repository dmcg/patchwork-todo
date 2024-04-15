import java.util.UUID

class Todos(
    items: List<TodoItem>
) : List<String> by (items.map {
    it.name
}) {

}

fun Todos(items: List<String>) = Todos(
    items.map { name -> TodoItem(UUID.randomUUID(), name) }
)

data class TodoItem(
    val id: UUID,
    val name: String,
)

