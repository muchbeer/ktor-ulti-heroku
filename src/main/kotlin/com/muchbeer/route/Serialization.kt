package com.muchbeer.route

import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.databind.*
import com.muchbeer.di.koinModule
import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureSerialization() {

    install(Koin) {
        slf4jLogger()
        modules(koinModule)
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
