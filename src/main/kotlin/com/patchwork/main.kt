package com.patchwork

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

fun main() {
    println("Hello World")
    val response = Response(Status.OK)
    val handler = { request: Request ->  response }
}