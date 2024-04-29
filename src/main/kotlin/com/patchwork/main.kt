package com.patchwork

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    handler.asServer(Undertow(port = 8080)).start()
}

private val response = Response(Status.OK).body("BTW Buy milk")
val handler = { request: Request ->  response }
fun handler(items: List<ToDoItem>): HttpHandler {
    return { request: Request -> Response(Status.OK).body(items.first().name) }
}

data class ToDoItem(val name: String) {

}