import java.util.UUID

class Todos(
    listOf: List<String>
) : List<String> by listOf {

}

data class TodoItem(
    val id: UUID,
    val name: String,
)

