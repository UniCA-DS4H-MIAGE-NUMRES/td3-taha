package fr.unice.miage.tp3.pizzap.repository

import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.model.Order
import kotlinx.coroutines.flow.Flow


expect class OrderRepository(dao: OrderDao) {
    fun getOrders(): Flow<List<Order>>
    suspend fun addOrder(order: Order)
    suspend fun deleteAllOrders()
}