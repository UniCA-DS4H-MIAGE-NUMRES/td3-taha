package fr.unice.miage.tp3.pizzap.repository

import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.model.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

actual class OrderRepository actual constructor(private val dao: OrderDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    actual fun getOrders(): Flow<List<Order>> {
        coroutineScope.launch {
            dao.refreshOrders()
        }
        return dao.getAllOrders()
    }

    actual suspend fun addOrder(order: Order) {
        withContext(Dispatchers.IO) {
            dao.insertOrder(order)
            dao.refreshOrders()
        }
    }

    actual suspend fun deleteAllOrders() {
        withContext(Dispatchers.IO) {
            dao.deleteAllOrders()
            dao.refreshOrders()
        }
    }
}
