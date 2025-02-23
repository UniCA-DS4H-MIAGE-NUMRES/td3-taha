package fr.unice.miage.tp3.pizzap.repository

import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.model.Order
import kotlinx.coroutines.flow.Flow

actual class OrderRepository actual constructor(private val dao: OrderDao) {
    actual fun getOrders(): Flow<List<Order>> = dao.getAllOrders()

    actual suspend fun addOrder(order: Order) {
        dao.insertOrder(order)
    }

    actual suspend fun deleteAllOrders() {
        dao.deleteAllOrders()
    }
}