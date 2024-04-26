package com.decoded.imagepoem

import android.app.Application
import androidx.room.Room
import com.decoded.imagepoem.data.AppDatabase

class ImagePoem : Application() {

    companion object {
        lateinit var database: AppDatabase // This will hold the database instance
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "your_database_name")
            .build()
    }
}