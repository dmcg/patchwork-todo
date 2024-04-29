package com.patchwork

import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    val items = mutableListOf<ToDoItem>()
    items.toHandler().asServer(Undertow(port = 8080)).start()
}

fun MutableList<ToDoItem>.toHandler(): HttpHandler = routes(
    "/items" bind Method.GET to this.toGetHandler(),
    "/items" bind Method.POST to this.toPostHandler()
)

private fun MutableList<ToDoItem>.toPostHandler() = { request: Request ->
    this.add(ToDoItem(request.bodyString()))
    Response(Status.CREATED)
}

private fun List<ToDoItem>.toGetHandler():HttpHandler =
    { Response(Status.OK).body(joinToString("\n") { it.name }) }

data class ToDoItem(val name: String)