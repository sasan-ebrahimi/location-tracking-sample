package com.sebrahimi.motiontagdemo

import android.app.Application
import androidx.room.Room
import com.sebrahimi.motiontagdemo.db.ConstDB
import com.sebrahimi.motiontagdemo.db.location.LocationDatabase
import com.sebrahimi.motiontagdemo.repo.LocationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideLocationDB(application: Application) : LocationDatabase {
        return Room.databaseBuilder(application, LocationDatabase::class.java,ConstDB.DB_NAME).build()
    }

    @Provides
    fun provideLocationRepo(locationDB: LocationDatabase): LocationRepo {
        return LocationRepo(locationDB)
    }

}