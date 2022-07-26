package com.muchbeer.route

import com.muchbeer.db.DatabaseFactory
import com.muchbeer.model.*
import com.muchbeer.repository.DataRepository
import com.muchbeer.repository.DataRepositoryImpl
import com.muchbeer.util.Constants
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.ktorm.database.Database
import java.io.File

fun Application.configureRouting() {

    val database : Database = DatabaseFactory.init()
    val repository : DataRepository = DataRepositoryImpl(database)

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

        post("/sendsms") {
            val receiveSmsContent = call.receiveOrNull<SmsContent>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val responseSMS = repository.sendSMS(
                phonNumb = receiveSmsContent.phone_number,
                message = receiveSmsContent.text_message
            )
            println("We receive number : ${receiveSmsContent.phone_number}")
            println("We receive message : ${receiveSmsContent.text_message}")
            responseSMS.forEach {
                println("We receive response : ${it?.status}")
            }

            call.respond( responseSMS)
         /*   when(response) {
                is DataState.Error -> call.respondText("Could not send sms")
                is DataState.ErrorException -> call.respondText("The error is : ${response.exception.message}")
                is DataState.Success -> call.respond(HttpStatusCode.OK,  response.data)
            }*/
        }
    }


    routing {
        var fileDescription = ""
        var fileName = ""

        post("/upload") {
            val multipartData = call.receiveMultipart()

            try {

                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            fileDescription = part.value
                        }
                        is PartData.FileItem -> {
                            fileName = part.originalFileName as String
                            val fileBytes = part.streamProvider().readBytes()
                     File("uploads/$fileName").writeBytes(fileBytes)

                        }
                        else -> Unit
                    }
                }
                val imageUrl = "${Constants.BASE_URL}/uploads/$fileName"
                //  call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
                call.respond(
                    HttpStatusCode.OK,
                    ImageUpload(
                        imageUrl = imageUrl,
                        fileName = fileName,
                        fileDescription = fileDescription
                    )
                )
            } catch (ex: Exception) {
                File("${Constants.IMAGE_PATH}/$fileName").delete()
                call.respond(HttpStatusCode.InternalServerError,"Image failed")
            }
        }
    }

}
