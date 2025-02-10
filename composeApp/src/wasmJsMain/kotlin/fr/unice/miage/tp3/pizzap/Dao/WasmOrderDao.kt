package fr.unice.miage.tp3.pizzap.Dao

import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.model.Order
import kotlinx.coroutines.flow.Flow

class WasmOrderDao : OrderDao {
    private val orders = mutableListOf<Order>()
    private val ordersFlow = mutableListOf<Order>()

    override suspend fun insertOrder(order: Order) {
        orders.add(order)
    }

    override suspend fun getOrderById(id: Int): Order? {
        return orders.find { it.id == id }
    }

    override suspend fun getAllOrders(): Flow<List<Order>> {
        return orders
    }

    override suspend fun deleteOrder(id: Int) {
        orders.removeIf { it.id == id }
    }
}
