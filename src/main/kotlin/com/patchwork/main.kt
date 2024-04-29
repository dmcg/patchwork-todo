package com.patchwork

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    println("Hello World")
    handler.asServer(Undertow(port = 8080)).start()
}

private val response = Response(Status.OK).body("Hello World")
val handler = { request: Request ->  response }