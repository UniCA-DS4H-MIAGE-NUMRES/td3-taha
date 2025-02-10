package fr.unice.miage.tp3.pizzap.datasource

import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.model.Order
import fr.unice.miage.tp3.pizzap.model.Pizza
import kotlinx.coroutines.flow.Flow
import pizzapp.composeapp.generated.resources.*

class DataSource(private val orderDao: OrderDao) {

    val orderHistory: Flow<List<Order>> = orderDao.getAllOrders()

    suspend fun saveOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    suspend fun deleteAllOrder(id: Int) {
        orderDao.deleteOrder(id)
    }

    fun getPizzas(): List<Pizza> {
        return listOf(
            Pizza(1, "Marguerita", 8.0, "pizza1"),
            Pizza(2, "Capricciosa", 10.0, "pizza2"),
            Pizza(3, "Diavola", 9.0, "pizza3"),
            Pizza(4, "Quattro Stagioni", 11.0, "pizza4"),
            Pizza(5, "Quattro Formaggi", 12.0, "pizza5"),
            Pizza(6, "Marinara", 7.0, "pizza6"),
            Pizza(7, "Pepperoni", 9.0, "pizza7"),
            Pizza(8, "Prosciutto", 10.0, "pizza8"),
            Pizza(9 ,"Frutti di Mare", 13.0, "pizza9")
        )


    }
}