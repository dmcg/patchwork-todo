package com.patchwork

import org.http4k.client.ApacheClient
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class TodoServerTests : TodoContract() {
    private val server = items.toHandler().asServer(Undertow(8080))
    override val client = ClientFilters.SetBaseUriFrom(Uri.of("http://localhost:8080")).then(ApacheClient())

    @BeforeEach
    fun setup() {
        server.start()
    }

    @AfterEach
    fun stop() {
        server.stop()
    }

}