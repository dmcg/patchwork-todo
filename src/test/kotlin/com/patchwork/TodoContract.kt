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
import java.util.*

abstract class TodoContract {
    val items = mutableListOf<ToDoItem>()
    protected abstract val client: HttpHandler

    @Test
    fun `returns empty body for no items`() {
        expectThat(client(Request(Method.GET, "/items"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("")
        }
    }

    @Test
    fun `returns multiple items in body`() {
        client(Request(Method.POST, "/items").body("Buy milk"))
        client(Request(Method.POST, "/items").body("Buy bread"))
        expectThat(client(Request(Method.GET, "/items"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Buy milk\nBuy bread")
        }
    }

    @Test
    fun `can add an item`() {
        expectThat(client(Request(Method.POST, "/items").body("Buy beer"))) {
            status.isEqualTo(Status.CREATED)
        }
        expectThat(client(Request(Method.GET, "/items"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Buy beer")
        }
    }

    @Test
    fun `returns a single item by ID`() {
        client(Request(Method.POST, "/items").body("Buy milk"))
        client(Request(Method.POST, "/items").body("Buy bread"))
        expectThat(client(Request(Method.GET, "/items/${items.first().id}"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Buy milk")
        }
    }

    @Test
    fun `returns not found when no single item by ID`() {
        client(Request(Method.POST, "/items").body("Buy milk"))
        client(Request(Method.POST, "/items").body("Buy bread"))
        expectThat(client(Request(Method.GET, "/items/${UUID.randomUUID()}"))) {
            status.isEqualTo(Status.NOT_FOUND)
            bodyString.isEqualTo("")
        }
    }

    @Test
    fun `returns bad request when not a uuid`() {
        client(Request(Method.POST, "/items").body("Buy milk"))
        client(Request(Method.POST, "/items").body("Buy bread"))
        expectThat(client(Request(Method.GET, "/items/banana"))) {
            status.isEqualTo(Status.BAD_REQUEST)
        }
    }
}