package com.muchbeer

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.muchbeer.route.*

fun main() {
    embeddedServer(Netty,  System.getenv("PORT").toInt()) {
   // embeddedServer(Netty, port = 8081, host = "0.0.0.0") {

        configureSerialization()
        configureRouting()

    }.start(wait = true)
}
