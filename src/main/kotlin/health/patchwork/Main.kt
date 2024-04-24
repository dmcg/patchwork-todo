package health.patchwork

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.lens.Path
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.rmi.server.UID
import java.util.*

fun main() {
    val toDos: List<Item> = listOf(
        Item(UUID.randomUUID(), "Buy Milk"),
        Item(UUID.randomUUID(),"Buy Coffee")
    )
    toDos.handler().asServer(Undertow(8080)).start()
}

fun List<Item>.handler() = routes(
    "/" bind Method.GET to rootHandler,
    "/listToDos" bind Method.GET to listHandler(),
    "/listToDos/{id}" bind Method.GET to itemHandler()
)

private val rootHandler =  {request: Request -> Response(Status.OK).body("hello")}

private fun List<Item>.listHandler(): (Request) -> Response = { request: Request ->
    Response(Status.OK).body(this.joinToString("\n") { it.name })
}

val idLens = Path.of("id")

private fun List<Item>.itemHandler(): (Request) -> Response = { request: Request ->
    try {
        val id = UUID.fromString(idLens.extract(request))
        val item: Item = this.find { it.id == id }!!
        Response(Status.OK).body(item.name)
    } catch (ex : IllegalArgumentException) {
        Response(Status.BAD_REQUEST).body(ex.message!!)
    }
}
