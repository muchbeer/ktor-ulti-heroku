package com.muchbeer.repository

import com.muchbeer.db.SchoolTable
import com.muchbeer.db.SchoolTable.region
import com.muchbeer.db.SchoolTable.school
import com.muchbeer.db.SchoolTable.sex
import com.muchbeer.db.UssdTable
import com.muchbeer.db.UssdTable.networkCode
import com.muchbeer.db.UssdTable.phoneNumber
import com.muchbeer.db.UssdTable.serviceCode
import com.muchbeer.db.UssdTable.sessionId
import com.muchbeer.db.UssdTable.text
import com.muchbeer.model.DataState
import com.muchbeer.model.ImageUpload
import com.muchbeer.model.School
import com.muchbeer.model.USSDModel
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import java.io.IOException

class DataRepositoryImpl(private val ktormDB : Database) : DataRepository {
    override suspend fun retrieveAllSchool(): List<School> {
        return ktormDB.sequenceOf(SchoolTable).toList().map {
            School(
                id = it.id,
                school = it.school,
                region = it.region,
                sex = it.sex
            )
        }
    }

    override suspend fun findSchoolByName(name: String): School? {
        val schoolEntity = ktormDB.sequenceOf(SchoolTable).firstOrNull {
            it.school eq name
        }?.let { School(id = it.id, school = it.school, region = it.region, sex = it.sex) }
        return schoolEntity
    }

    override suspend fun insertSchool(mSchool: School): School {
        val schoolID: Int = ktormDB.insertAndGenerateKey(SchoolTable) {
            set(school, mSchool.school)
            set(region, mSchool.region)
            set(sex, mSchool.sex)
        } as Int

        return School(
            id = schoolID, school = mSchool.school,
            region = mSchool.region, sex = mSchool.sex
        )
    }

    override suspend fun retrieveAllUSSD(): List<USSDModel> {
        return ktormDB.sequenceOf(UssdTable).toList().map {
            USSDModel(
                sessionId = it.sessionId,
                phoneNumber = it.phoneNumber,
                networkCode = it.networkCode,
                serviceCode = it.serviceCode,
                text = it.text
            )
        }
    }

    override suspend fun findUSSDSessionById(msessionID: String): USSDModel? {
        return ktormDB.sequenceOf(UssdTable).firstOrNull() {
            it.sessionId eq msessionID
        }?.let {
            USSDModel(
                sessionId = it.sessionId, phoneNumber = it.phoneNumber,
                networkCode = it.networkCode, serviceCode = it.serviceCode, text = it.text
            )
        }

    }

    override suspend fun updateSessionId(msessionID: String, mUSSD: USSDModel): USSDModel {
        val updteType = ktormDB.update(UssdTable) {
            set(sessionId, mUSSD.sessionId)
            set(phoneNumber, mUSSD.phoneNumber)
            set(serviceCode, mUSSD.serviceCode)
            set(networkCode, mUSSD.networkCode)
            set(text, mUSSD.text)

            where { it.sessionId eq msessionID }
        }
        return USSDModel(
            sessionId = mUSSD.sessionId,
            phoneNumber = mUSSD.phoneNumber,
            networkCode = mUSSD.networkCode,
            serviceCode = mUSSD.serviceCode,
            text = mUSSD.text
        )
    }

    override suspend fun insertUSSD(ussdModel: USSDModel): USSDModel {
        val ussdID: Int = ktormDB.insert(UssdTable) {
            set(sessionId, ussdModel.sessionId)
            set(phoneNumber, ussdModel.phoneNumber)
            set(networkCode, ussdModel.networkCode)
            set(serviceCode, ussdModel.serviceCode)
            set(text, ussdModel.text)
        }


        return if (ussdID >= 0) USSDModel(
            sessionId = ussdModel.sessionId,
            phoneNumber = ussdModel.phoneNumber,
            networkCode = ussdModel.networkCode,
            serviceCode = ussdModel.serviceCode,
            text = ussdModel.text
        ) else USSDModel("2", "0755", "123", "000", "nothing entered")

    }

/*    override suspend fun sendSMS(phonNumb: String, message: String) : DataState<List<Recipient?>>{
        val username = "muchbeer"
        val apiKey = "1b949d6294137d690f976b69c670a25594d12a5207237412c416a625181b1375"

        AfricasTalking.initialize(username, apiKey)

        val sms : SmsService = AfricasTalking.getService(AfricasTalking.SERVICE_SMS)
        val recepient = arrayOf(phonNumb)
        val from = "AFRICASTKNG"

        return try {
            DataState.Success(sms.send(message, from, recepient, true))
        } catch (ex : Exception) {
            DataState.ErrorException(exception = ex)
        } catch (io : Throwable) {
            DataState.Error(error = io.message.toString())
        }
    }*/
}