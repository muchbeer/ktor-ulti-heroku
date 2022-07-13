package com.muchbeer.db

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object SchoolTable : Table<SchoolEntity>("school") {
    val id  = int("id").primaryKey().bindTo { it.id }
    val school = varchar("school").bindTo { it.school }
    val region = varchar("region").bindTo { it.region }
    val sex = varchar("sex").bindTo { it.sex }
}

interface SchoolEntity : Entity<SchoolEntity> {

    companion object : Entity.Factory<SchoolEntity>()
    val id : Int
    val school : String
    val region : String
    val sex : String

}


object UssdTable : Table<UssdEntity>("ussd"){

    val sessionId = varchar("sessionId").primaryKey().bindTo { it.sessionId }
    val phoneNumber = varchar("phoneNumber").bindTo { it.phoneNumber }
    val networkCode = varchar("networkCode").bindTo { it.networkCode }
    val serviceCode = varchar("serviceCode").bindTo { it.serviceCode }
    val text = varchar("text").bindTo { it.text }
}

interface UssdEntity : Entity<UssdEntity> {

    companion object : Entity.Factory<UssdEntity>()
    val sessionId : String
    val phoneNumber : String
    val networkCode : String
    val serviceCode : String
    val text : String
}