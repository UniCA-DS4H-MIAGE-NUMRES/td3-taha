package fr.unice.miage.tp3.pizzap.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.unice.miage.tp3.pizzap.model.OrderEntity
import fr.unice.miage.tp3.pizzap.converters.OrderConverters
import fr.unice.miage.tp3.pizzap.dao.AndroidOrderDao

/**
 * Classe qui définit la base de données Room pour Android
 */
@Database(entities = [OrderEntity::class], version = 2, exportSchema = false) // 🔥 Increment version
@TypeConverters(OrderConverters::class)
abstract class PizzaDatabase : RoomDatabase() {
    abstract fun orderDao(): AndroidOrderDao

    companion object {
        @Volatile
        private var INSTANCE: PizzaDatabase? = null

        fun getDatabase(context: Context): PizzaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PizzaDatabase::class.java,
                    "pizzap_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
