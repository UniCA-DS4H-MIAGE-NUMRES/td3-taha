package fr.unice.miage.tp3.pizzap.dao

import fr.unice.miage.tp3.pizzap.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderDao {
    suspend fun insertOrder(order: Order)
    fun getAllOrders(): Flow<List<Order>>
    suspend fun deleteAllOrders()
    suspend fun refreshOrders()
    abstract suspend fun deleteOrder(id: Int)
}