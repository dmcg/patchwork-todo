package health.patchwork

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    val toDos: List<Item> = listOf(Item("Buy Milk"), Item("Buy Coffee"))
    toDos.handler().asServer(Undertow(8080)).start()
}

fun List<Item>.handler() = routes(
    "/" bind Method.GET to rootHandler,
    "/listToDos" bind Method.GET to listHandler()
)

private val rootHandler =  {request: Request -> Response(Status.OK).body("hello")}
private fun List<Item>.listHandler(): (Request) -> Response = { request: Request ->
    Response(Status.OK).body(this.joinToString("\n") { it.name })
}
