package com.patchwork

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    val items = emptyList<ToDoItem>()
    items.toHandler().asServer(Undertow(port = 8080)).start()
}

fun List<ToDoItem>.toHandler(): HttpHandler {
    return {
        Response(Status.OK).body(joinToString("\n") { it.name })
    }
}

data class ToDoItem(val name: String) {

}