package fr.unice.miage.tp3.pizzap.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.unice.miage.tp3.pizzap.model.Order
import fr.unice.miage.tp3.pizzap.model.OrderEntity
import fr.unice.miage.tp3.pizzap.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


@Dao
interface AndroidOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders ORDER BY orderId DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()

    @Query("SELECT * FROM orders")
    suspend fun refreshOrders(): List<OrderEntity>

    @Query("DELETE FROM orders WHERE orderId = :id")
    suspend fun deleteOrder(id: Int)
}
