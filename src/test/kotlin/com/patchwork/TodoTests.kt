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
    val items = mutableListOf<ToDoItem>()
    val client: HttpHandler = items.toHandler()

    @Test
    fun `returns empty body for no items`() {
        expectThat(client(Request(Method.GET, "http://localhost:8080/"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("")
        }
    }

    @Test
    fun `returns multiple items in body`() {
        items.addAll(listOf(ToDoItem("Buy milk"), ToDoItem("Buy bread")))
        expectThat(client(Request(Method.GET, "http://localhost:8080/"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Buy milk\nBuy bread")
        }
    }
}