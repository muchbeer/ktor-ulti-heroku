package com.muchbeer.di

import com.muchbeer.db.DatabaseFactory
import com.muchbeer.repository.DataRepository
import com.muchbeer.repository.DataRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single { DatabaseFactory.init() }

    single<DataRepository> { DataRepositoryImpl(get()) }

    // single<UserService> { UserServiceImpl(get()) }
}