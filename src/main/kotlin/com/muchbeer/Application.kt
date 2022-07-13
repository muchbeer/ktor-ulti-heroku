package com.muchbeer

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.muchbeer.route.*

fun main() {
    embeddedServer(Netty,  System.getenv("PORT").toInt()) {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
