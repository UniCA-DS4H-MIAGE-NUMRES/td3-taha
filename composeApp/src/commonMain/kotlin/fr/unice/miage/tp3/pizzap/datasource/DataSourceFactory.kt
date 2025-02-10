package fr.unice.miage.tp3.pizzap.datasource

import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.repository.OrderRepository

object DataSourceFactory {
    private lateinit var instance: DataSource
    private lateinit var orderRepository: OrderRepository

    fun init(orderDao: OrderDao) {
        instance = DataSource(orderDao)
        orderRepository = OrderRepository(orderDao)
    }

    fun getInstance(): DataSource {
        if (!::instance.isInitialized) throw IllegalStateException("DataSourceFactory not initialized")
        return instance
    }

    fun getOrderRepository(): OrderRepository {
        if (!::orderRepository.isInitialized) throw IllegalStateException("OrderRepository has not been initialized.")
        return orderRepository
    }
}