package com.patchwork

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

class TodoTests {
    private val items = mutableListOf<ToDoItem>()
    private val client = items.toHandler()

    @Test
    fun `returns empty body for no items`() {
        expectThat(client(Request(Method.GET, "/items"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("")
        }
    }

    @Test
    fun `returns multiple items in body`() {
        items.addAll(listOf(ToDoItem("Buy milk"), ToDoItem("Buy bread")))
        expectThat(client(Request(Method.GET, "/items"))) {
            status.isEqualTo(Status.OK)
            bodyString.isEqualTo("Buy milk\nBuy bread")
        }
    }

    @Test
    fun `can add an item`(){
        expectThat(client(Request(Method.POST, "/items").body("Buy beer"))) {
            status.isEqualTo(Status.CREATED)
        }
        expectThat(items).hasSize(1).and { get { first().name }.isEqualTo("Buy beer")  }
    }
}