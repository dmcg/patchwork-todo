package health.patchwork

import java.util.UUID

class Item(
    val id: UUID,
    val name: String
){
    init {
        require(name.isNotBlank()){"Name must not be blank"}
    }
}
