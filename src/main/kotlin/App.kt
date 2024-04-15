import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    println("Something happened!!")
    val items = Todos(listOf("item1", "item2", "item3"))
    items.routes.asServer(Jetty(8080)).start()
}

// Leaving _ parameter to show options, should be internally consistent
val rootHandler = { _: Request ->
    Response(status = Status.OK).body("Something happened!!")
}

val Todos.listHandler: HttpHandler
    get() = {
        Response(Status.OK).body(
            this.joinToString(separator = "\n") { it.name }
        )
    }

val Todos.routes: HttpHandler
    get() = routes(
        "/" bind Method.GET to rootHandler,
        "/items" bind Method.GET to this.listHandler
    )