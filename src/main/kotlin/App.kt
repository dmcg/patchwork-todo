import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    println("Something happened!!")
    routes.asServer(Jetty(8080)).start()
}

val rootHandler = { _: Request ->
    Response(status = Status.OK).body("Something happened!!")
}

val itemsHandler: HttpHandler = {
    val items = listOf("item1", "item2", "item3")
    Response(Status.OK).body(items.joinToString())
}

val routes: HttpHandler = routes(
    "/" bind Method.GET to rootHandler,
    "/items" bind Method.GET to itemsHandler
)