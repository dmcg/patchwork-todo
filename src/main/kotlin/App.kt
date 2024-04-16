import org.http4k.core.*
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.ServerFilters
import org.http4k.lens.BiDiPathLens
import org.http4k.lens.Path
import org.http4k.lens.value
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import java.util.UUID

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

val idLens = Path.of("id")

val Todos.deleteHandler: HttpHandler
    get() = {
        val id = UUID.fromString(idLens(it))
        this.deleteItemById(id)
        Response(Status.OK)
    }

val Todos.routes: HttpHandler
    get() = ServerFilters
        .CatchAll().then(ServerFilters.CatchLensFailure())
        .then(DebuggingFilters.PrintRequestAndResponse())
        .then(
            routes(
                "/" bind Method.GET to rootHandler,
                "/items" bind Method.GET to this.listHandler,
                "/item/{id}" bind Method.DELETE to this.deleteHandler
            )
        )