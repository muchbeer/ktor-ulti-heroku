package com.muchbeer

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.muchbeer.route.*

fun main() {
    embeddedServer(Netty, port = 8085, host = "127.0.0.1") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
