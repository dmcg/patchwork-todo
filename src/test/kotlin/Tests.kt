import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {
    @Test
    fun test() {
        println("Hello world!")
    }

    @Test
    fun testHandler(){
        val response = handler.invoke(Request(Method.GET, "/"))
        assertEquals(Status.OK, response.status)
    }
}