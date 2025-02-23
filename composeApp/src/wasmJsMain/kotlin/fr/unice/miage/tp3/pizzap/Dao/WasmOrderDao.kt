package fr.unice.miage.tp3.pizzap.Dao

import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class WasmOrderDao : OrderDao {
    private val orders = MutableStateFlow<List<Order>>(emptyList())

    override suspend fun insertOrder(order: Order) {
        val currentOrders = orders.value.toMutableList()
        currentOrders.add(order)
        orders.value = currentOrders
    }

    override fun getAllOrders(): Flow<List<Order>> = orders

    override suspend fun deleteAllOrders() {
        orders.value = emptyList()
    }

    override suspend fun refreshOrders() {
        }

    override suspend fun deleteOrder(id: Int) {
        TODO("Not yet implemented")
    }
}
