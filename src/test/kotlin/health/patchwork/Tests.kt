package health.patchwork

import org.http4k.core.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Tests {
    val toDos: MutableList<Item> = mutableListOf(
        Item(UUID.randomUUID(),"Buy Milk"),
        Item(UUID.randomUUID(),"Buy Coffee")
    )
    val handler: HttpHandler = toDos.handler()

    @Test
    fun testHandler(){
        val response = handler(Request(Method.GET, "/"))
        assertEquals(Status.OK, response.status)
        assertEquals("hello", response.bodyString())
    }

    @Test
    fun listToDos() {
        assertEquals(
            Response(Status.OK).body("Buy Milk\nBuy Coffee"),
            handler(Request(Method.GET, "/listToDos"))
        )
    }

    @Test
    fun listToDo() {
        val coffeeId = toDos[1].id
        assertEquals(
            Response(Status.OK).body("Buy Coffee"),
            handler(Request(Method.GET, "/listToDos/$coffeeId"))
        )
    }

    @Test
    fun `list to do with wrong id format`() {
        val wrongFormatId = -1
        assertEquals(
            Response(Status.BAD_REQUEST).body("Invalid UUID string"),
            handler(Request(Method.GET, "/listToDos/$wrongFormatId"))
        )
    }

    @Test
    fun `list to do with missing id`() {
        val missingUUID = UUID.randomUUID()
        assertEquals(
            Response(Status.NOT_FOUND),
            handler(Request(Method.GET, "/listToDos/$missingUUID"))
        )
    }

    @Test
    fun `it should add posted item`() {
        assertFalse(toDos.any { it.name == "Buy Sugar" })

        val response = handler(Request(Method.POST, "/listToDos").body("Buy Sugar"))
        assertEquals(
            Status.CREATED,
            response.status
        )
        assertDoesNotThrow { UUID.fromString(response.bodyString()) }
        assertTrue(toDos.any { it.name == "Buy Sugar" })
    }
    @Test
    fun `it should fail with blank name`() {
        assertEquals(2, toDos.size)
        val response = handler(Request(Method.POST, "/listToDos"))
        assertEquals(
            Status.BAD_REQUEST,
            response.status
        )
        assertEquals(2, toDos.size)
    }
}