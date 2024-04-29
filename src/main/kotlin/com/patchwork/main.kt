package com.patchwork

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    val items = emptyList<ToDoItem>()
    handler(items).asServer(Undertow(port = 8080)).start()
}

fun handler(items: List<ToDoItem>): HttpHandler {
    return {
        val first: ToDoItem? = items.firstOrNull()
        first?.let {
            Response(Status.OK).body(first.name)
        } ?:Response(Status.OK)
    }
}

data class ToDoItem(val name: String) {

}