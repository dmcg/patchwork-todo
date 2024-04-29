package com.patchwork

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TodoTests {
    @Test
    fun `returns empty body for no items`() {
        val items = emptyList<ToDoItem>()
        val client: HttpHandler = items.toHandler()
        expectThat(client(Request(Method.GET, "http://localhost:8080/"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("")
        }
    }

    @Test
    fun `returns items in body for each items`() {
        val items = listOf(ToDoItem("BTW Buy milk"))
        val client: HttpHandler = items.toHandler()
        expectThat(client(Request(Method.GET, "http://localhost:8080/"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("BTW Buy milk")
        }
    }
}