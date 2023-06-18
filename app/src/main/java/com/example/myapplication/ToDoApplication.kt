package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.core.AppDataBase
import io.realm.Realm
import io.realm.RealmConfiguration

class ToDoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        /*Realm.init(this)
        val configRealm=RealmConfiguration.Builder()
            .name("todo.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(configRealm)*/

       /* val db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "To-do-Database"
        ).build()*/

    }
    val databaseService by lazy {
        AppDataBase.getDatabase(applicationContext).dataBaseService()
    }
}