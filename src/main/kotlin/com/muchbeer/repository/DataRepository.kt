package com.muchbeer.repository

import com.muchbeer.model.School

interface DataRepository {
    suspend fun retrieveAllSchool(): List<School>

    suspend fun findSchoolByName(name: String): School?

    suspend fun insertSchool(mSchool: School): School


}