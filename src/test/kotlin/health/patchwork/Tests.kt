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
        val anItem = Item("Buy Milk")
        val secondItem = Item("Buy Coffee")
        val toDos: List<Item> = listOf(anItem, secondItem)

        val expectedResponse = Response(Status.OK).body("Buy Milk\nBuy Coffee")
        val handler: HttpHandler = TODO()
        val response = handler(Request(Method.GET, "/listToDos"))

        assertEquals(expectedResponse, response)
    }
}