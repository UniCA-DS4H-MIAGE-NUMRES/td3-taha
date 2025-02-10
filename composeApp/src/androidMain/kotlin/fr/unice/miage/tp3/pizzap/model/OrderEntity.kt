package fr.unice.miage.tp3.pizzap.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.unice.miage.tp3.pizzap.converters.OrderConverters

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val date: String,
    val totalPrice: Double,
    @TypeConverters(OrderConverters::class) val items: List<CartItem>
)

fun OrderEntity.toOrder(): Order {
    return Order(orderId, date, items, totalPrice )
}

fun Order.toEntity(): OrderEntity {
    return OrderEntity(id, timestamp, totalPrice, items)
}
