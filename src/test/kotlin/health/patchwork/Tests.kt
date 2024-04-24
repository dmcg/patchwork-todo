package health.patchwork

import org.http4k.core.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {
    @Test
    fun test() {
        println("Hello world!")
    }

    @Test
    fun testHandler(){
        val response = handler(Request(Method.GET, "/"))
        assertEquals(Status.OK, response.status)
    }

    @Test
    fun listToDos() {
        val toDos: List<Item> = listOf(Item("Buy Milk"), Item("Buy Coffee"))
        val handler: HttpHandler = toDos.handler()

        assertEquals(
            Response(Status.OK).body("Buy Milk\nBuy Coffee"),
            handler(Request(Method.GET, "/listToDos"))
        )
    }
}