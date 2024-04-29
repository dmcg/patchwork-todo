package com.patchwork

import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.util.UUID

fun main() {
    val items = mutableListOf<ToDoItem>()
    items.toHandler().asServer(Undertow(port = 8080)).start()
}

fun MutableList<ToDoItem>.toHandler(): HttpHandler = routes(
    "/items" bind routes(
        "/" bind Method.GET to this.toGetHandler(),
        "/{id}" bind Method.GET to this.toGetItemHandler(),
        "/" bind Method.POST to this.toPostHandler()
    )
)

private fun MutableList<ToDoItem>.toPostHandler() = { request: Request ->
    this.add(ToDoItem(request.bodyString()))
    Response(Status.CREATED)
}

private fun List<ToDoItem>.toGetHandler(): HttpHandler =
    { Response(Status.OK).body(joinToString("\n") { it.name }) }

private fun List<ToDoItem>.toGetItemHandler(): HttpHandler =
    { request ->
        val path = UUID.fromString(request.path("id"))
        val item = this.find { item -> item.id == path }?: error("Not found ${path}")
        Response(Status.OK).body(item.name)
    }

data class ToDoItem(
    val id: UUID,
    val name: String,
) {
    constructor(name: String) : this(UUID.randomUUID(), name)
}