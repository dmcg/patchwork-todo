package health.patchwork

import org.http4k.core.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {
    val toDos: List<Item> = listOf(Item("Buy Milk"), Item("Buy Coffee"))
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
}