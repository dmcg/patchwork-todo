package health.patchwork

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    val toDos: List<Item> = listOf(Item("Buy Milk"), Item("Buy Coffee"))
    toDos.handler().asServer(Undertow(8080)).start()
}

fun List<Item>.handler(): (request: Request) -> Response {
    return { request ->
        if (request.uri.path == "/")
            Response(Status.OK).body("hello")
        else
            Response(Status.OK).body(this.joinToString("\n") { it.name }) }
}