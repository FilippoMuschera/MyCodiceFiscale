package com.muschera.mycodicefiscale.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(
    entities = [City::class, SavedCF::class],
    version = 1
) //la version serve ad android per confronatare la versione del db presente sul dispositivo con quello che
//stiamo creando. Se sono diverse deve fare migrazione DB.
abstract class DBCity : RoomDatabase() {
    companion object {
        private var db: DBCity? = null // Singleton
        fun getInstance(context: Context): DBCity {
            if (db == null) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    DBCity::class.java,
                    "city.db"
                )
                    .createFromAsset("city.db")
                    .build()
            }
            return db as DBCity
        }
    }

    abstract fun cityDAO(): DaoCity
}

