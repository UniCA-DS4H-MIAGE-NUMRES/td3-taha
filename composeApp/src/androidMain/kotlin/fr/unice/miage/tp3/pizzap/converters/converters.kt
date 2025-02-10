package fr.unice.miage.tp3.pizzap.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.unice.miage.tp3.pizzap.model.CartItem

class OrderConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromOrderItemList(orderItems: List<CartItem>?): String {
        return gson.toJson(orderItems)
    }

    @TypeConverter
    fun toOrderItemList(orderItemsString: String?): List<CartItem> {
        return if (orderItemsString.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<CartItem>>() {}.type
            val orderItems = gson.fromJson<List<CartItem>>(orderItemsString, type)

            orderItems.forEach { it.pizza = it.pizza.copy(imagePath = it.pizza.imagePath.toString()) }

            orderItems
        }
    }
}
