package com.patchwork

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    val items = mutableListOf<ToDoItem>()
    items.toHandler().asServer(Undertow(port = 8080)).start()
}

fun MutableList<ToDoItem>.toHandler(): HttpHandler {
    return { request ->
        when (request.method) {
            Method.GET -> {
                val l = function()
                l(request)
            }
            Method.POST -> {
                this.add(ToDoItem(request.bodyString()))
                Response(Status.CREATED)
            }

            else -> TODO()
        }
    }
}

private fun MutableList<ToDoItem>.function():HttpHandler =
    { Response(Status.OK).body(joinToString("\n") { it.name }) }

data class ToDoItem(val name: String)