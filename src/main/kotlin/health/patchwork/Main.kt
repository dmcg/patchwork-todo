package health.patchwork

import org.http4k.core.*
import org.http4k.filter.ServerFilters
import org.http4k.lens.Path
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.util.*

fun main() {
    val toDos: MutableList<Item> = mutableListOf(
        Item(UUID.randomUUID(), "Buy Milk"),
        Item(UUID.randomUUID(),"Buy Coffee")
    )
    toDos.handler().asServer(Undertow(8080)).start()
}

fun MutableList<Item>.handler() =
    ServerFilters.CatchAll().then(
    routes(
    "/" bind Method.GET to rootHandler,
    "/listToDos" bind Method.GET to listHandler(),
    "/listToDos/{id}" bind Method.GET to itemHandler(),
    "/listToDos" bind Method.POST to addHandler()
    )
)

private val rootHandler =  {request: Request -> Response(Status.OK).body("hello")}

private fun List<Item>.listHandler(): (Request) -> Response = { request: Request ->
    Response(Status.OK).body(this.joinToString("\n") { it.name })
}

private fun MutableList<Item>.addHandler(): (Request) -> Response = { request: Request ->
    val name = request.bodyString()
    if (name.isNotBlank()) {
        val newItem = Item(UUID.randomUUID(), name)
        this += newItem
        Response(Status.CREATED).body(newItem.id.toString())
    } else {
        Response(Status.BAD_REQUEST)
    }
}

val idLens = Path.of("id")

private fun List<Item>.itemHandler(): (Request) -> Response =
    fun(request: Request): Response {
        val id: UUID = idLens.extract(request).toUUID() ?:
            return Response(Status.BAD_REQUEST).body("Invalid UUID string")
        return when (val item = find { it.id == id }) {
            null -> Response(Status.NOT_FOUND)
            else -> Response(Status.OK).body(item.name)
        }
    }

fun String.toUUID(): UUID? = try {
    UUID.fromString(this)
} catch (x: IllegalArgumentException) {
    null
}

