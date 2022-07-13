package com.muchbeer.route

import com.muchbeer.model.School
import com.muchbeer.model.USSDModel
import com.muchbeer.repository.DataRepository
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val repository : DataRepository by inject()

    val listTem: List<School> = listOf(
        School(1, "Lubaga", "Shy", "Both"),
        School(1, "Loyola", "Dar", "Both")
    )

    routing {
        get("/") {
            call.respondText("Change to Ultimate brother")
        }

        get("/gadiel") {
            call.respond(mapOf("data" to listTem))
        }
    }

    routing {

        get("/schools") {
            val retrieveSchool: List<School> = repository.retrieveAllSchool()
            call.respond(retrieveSchool)
        }


        post("/register") {
            //    call.response.headers.append("Content-Type", "application/json")

            val addSchool = call.receiveOrNull<School>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }


            val response: School = repository.insertSchool(addSchool)

            call.respond(status = HttpStatusCode.OK, response)
        }

        post("/ussd") {
            val addUssd = call.receive<USSDModel>()

            if (repository.findUSSDSessionById(addUssd.sessionId) != null) {
                repository.updateSessionId(addUssd.sessionId, addUssd)
                call.respond(status = HttpStatusCode.OK, message = "Success ")
            } else {
                val response = repository.insertUSSD(addUssd)
                call.respond(status = HttpStatusCode.OK, response)
            }

        }

    }
}
