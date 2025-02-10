package fr.unice.miage.tp3.pizzap.repository

import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.model.Order
import kotlinx.coroutines.flow.Flow

actual class OrderRepository actual constructor(dao: OrderDao) {
    actual fun getOrders(): Flow<List<Order>> {
        TODO("Not yet implemented")
    }

    actual suspend fun addOrder(order: Order) {
    }

    actual suspend fun deleteAllOrders() {
    }
}