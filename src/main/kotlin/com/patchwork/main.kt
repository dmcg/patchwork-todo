package com.patchwork

import org.http4k.core.*
import org.http4k.filter.ServerFilters
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

fun MutableList<ToDoItem>.toHandler(): HttpHandler =
    ServerFilters.CatchAll().then(
        routes(
            "/items" bind routes(
                "/{id}" bind Method.GET to this.toGetItemHandler(),
                "/" bind Method.GET to this.toGetHandler(),
                "/" bind Method.POST to this.toPostHandler()
            )
        )
    )

private fun MutableList<ToDoItem>.toPostHandler() = { request: Request ->
    this.add(ToDoItem(request.bodyString()))
    Response(Status.CREATED)
}

private fun List<ToDoItem>.toGetHandler(): HttpHandler =
    { Response(Status.OK).body(joinToString("\n") { it.name }) }

private fun List<ToDoItem>.toGetItemHandler(): HttpHandler =
    fun(request: Request): Response {
        val path = request.path("id") ?: return Response(Status.NOT_FOUND)
        val uuid = path.toUUID() ?: return Response(Status.BAD_REQUEST)
        val item = find { item -> item.id == uuid } ?: return Response(Status.NOT_FOUND)
        return Response(Status.OK).body(item.name)
    }

private fun String.toUUID(): UUID? = try {
    UUID.fromString(this)
} catch (e: IllegalArgumentException) {
    null
}

data class ToDoItem(
    val id: UUID,
    val name: String,
) {
    constructor(name: String) : this(UUID.randomUUID(), name)
}