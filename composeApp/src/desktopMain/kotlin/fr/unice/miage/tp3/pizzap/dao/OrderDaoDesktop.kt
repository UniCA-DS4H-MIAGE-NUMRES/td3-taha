package fr.unice.miage.tp3.pizzap.dao

import fr.unice.miage.tp3.pizzap.datasource.DataSourceFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.sql.Connection
import java.sql.DriverManager
import fr.unice.miage.tp3.pizzap.model.Order
import fr.unice.miage.tp3.pizzap.model.CartItem
import fr.unice.miage.tp3.pizzap.model.Pizza

class OrderDaoDesktop : OrderDao {
    private val orders = MutableStateFlow<List<Order>>(emptyList())

    init {
        createTable()
    }

    private fun createTable() {
        val connection = getConnection()
        connection.createStatement().execute("DROP TABLE IF EXISTS orders")
        connection.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS orders (
                orderId INTEGER PRIMARY KEY AUTOINCREMENT,
                timeStamp TEXT,
                totalPrice REAL,
                items TEXT
            )
        """)
        connection.close()
    }

    private fun getPizzaById(pizzaId: Int): Pizza {
        return DataSourceFactory.getInstance().getPizzas()
            .find { it.id == pizzaId }
            ?: throw IllegalArgumentException("Pizza not found with id: $pizzaId")
    }

    private fun encodeCartItems(items: List<CartItem>): String {
        return items.joinToString(separator = ";") { item ->
            "${item.id},${item.pizza.id},${item.cheese}"
        }
    }

    private fun decodeCartItems(data: String): List<CartItem> {
        if (data.isBlank()) return emptyList()
        return data.split(";").map { itemString ->
            val parts = itemString.split(",")
            CartItem(
                id = parts[0].toInt(),
                pizza = getPizzaById(parts[1].toInt()),
                cheese = parts[2].toInt()  // use index 2 instead of 3
            )
        }
    }

    override suspend fun refreshOrders() {
        val connection = getConnection()
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM orders")
        val orderList = mutableListOf<Order>()

        while (resultSet.next()) {
            val order = Order(
                id = resultSet.getInt("orderId"),
                timestamp = resultSet.getString("timeStamp"),
                totalPrice = resultSet.getDouble("totalPrice"),
                items = decodeCartItems(resultSet.getString("items"))
            )
            orderList.add(order)
        }

        orders.value = orderList
        connection.close()
    }

    override suspend fun deleteOrder(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrder(order: Order) {
        val connection = getConnection()
        val statement = connection.prepareStatement(
            "INSERT INTO orders (timeStamp, totalPrice, items) VALUES (?, ?, ?)"
        )
        statement.setString(1, order.timestamp)
        statement.setDouble(2, order.totalPrice)
        statement.setString(3, encodeCartItems(order.items))
        statement.executeUpdate()
        connection.close()
        refreshOrders()
    }


    override fun getAllOrders(): Flow<List<Order>> = orders

    override suspend fun deleteAllOrders() {
        val connection = getConnection()
        connection.createStatement().execute("DELETE FROM orders")
        connection.close()
        refreshOrders()
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection("jdbc:sqlite:pizza_database.db")
    }
}