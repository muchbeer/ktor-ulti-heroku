package com.muchbeer.repository

import com.muchbeer.db.SchoolTable
import com.muchbeer.db.SchoolTable.region
import com.muchbeer.db.SchoolTable.school
import com.muchbeer.db.SchoolTable.sex
import com.muchbeer.model.School
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
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

}