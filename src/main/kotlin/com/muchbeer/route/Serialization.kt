package com.muchbeer.route

import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import org.slf4j.event.Level

fun Application.configureSerialization() {


    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(CallLogging) {
        level = Level.INFO
    }

}
