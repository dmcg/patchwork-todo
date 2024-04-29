package com.patchwork

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TodoTests {
    @Test
    fun test() {
        val items = listOf(ToDoItem("BTW Buy milk"))
        val client: HttpHandler = handler(items)
        val response = client(Request(Method.GET, "http://localhost:8080/"))
        assertEquals(Status.OK, response.status)
        assertEquals("BTW Buy milk", response.bodyString())
    }
}