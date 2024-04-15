import org.http4k.core.Method
import org.http4k.core.Request
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AppTest {
    @Test
    fun test(){
        handler.invoke(Request(Method.GET, "/root"))
    }
}