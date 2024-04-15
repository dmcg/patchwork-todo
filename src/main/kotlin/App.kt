import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Jetty
import org.http4k.server.asServer


fun main() {
    println("Something happened!!")
    handler.asServer(Jetty(8080)).start()
}

val handler: HttpHandler = {
    Response(status = Status.OK).body("Something happened")
}
