import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {

    handler.asServer(Undertow(8080)).start()


}
val handler : HttpHandler = { request -> Response(Status.OK).body("hello") }