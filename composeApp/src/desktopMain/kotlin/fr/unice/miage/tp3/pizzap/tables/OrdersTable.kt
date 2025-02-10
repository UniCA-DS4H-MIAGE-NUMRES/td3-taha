package fr.unice.miage.tp3.pizzap.tables

import com.sun.tools.javac.util.Name

object OrdersTable : Name.Table("orders") {
    val id = integer("id").autoIncrement()
    val timestamp = varchar("timestamp", 255)
    val totalPrice = double("total_price")
    override val primaryKey = PrimaryKey(id)
}