package fr.unice.miage.tp3.pizzap.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {
        Database.connect("jdbc:sqlite:pizza_database.db", driver = "org.sqlite.JDBC")
        return JdbcSqliteDriver("jdbc:sqlite:pizza_database.db")
    }
}