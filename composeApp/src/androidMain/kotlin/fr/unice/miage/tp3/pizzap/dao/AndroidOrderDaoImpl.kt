package fr.unice.miage.tp3.pizzap.dao

import fr.unice.miage.tp3.pizzap.model.Order
import fr.unice.miage.tp3.pizzap.model.toEntity
import fr.unice.miage.tp3.pizzap.model.toOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AndroidOrderDaoImpl(private val androidDao: AndroidOrderDao) : OrderDao {
    override suspend fun insertOrder(order: Order) {
        androidDao.insertOrder(order.toEntity())
    }


    override fun getAllOrders(): Flow<List<Order>> {
        val orderEntities = androidDao.getAllOrders()
        val orders = orderEntities.map { list -> list.map { it.toOrder() } }
        return orders
    }

    override suspend fun deleteAllOrders() {
        androidDao.deleteAllOrders()
    }

    override suspend fun refreshOrders() {
        androidDao.getAllOrders().first()
    }

    override suspend fun deleteOrder(id: Int) {
        androidDao.deleteOrder(id)
    }
}
