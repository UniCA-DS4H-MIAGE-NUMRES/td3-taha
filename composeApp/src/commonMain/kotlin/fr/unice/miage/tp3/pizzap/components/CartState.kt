package fr.unice.miage.tp3.pizzap.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import fr.unice.miage.tp3.pizzap.model.CartItem
import fr.unice.miage.tp3.pizzap.model.Pizza

data class CartTempItem(
    var pizza: Pizza,
    var cheese: Float
)

object CartState {
    private val _cart = mutableStateListOf<CartTempItem>()
    val cart: List<CartTempItem> get() = _cart

    fun addToCart(pizza: Pizza, extraCheese: MutableState<Float>) {
        val item = CartTempItem(pizza, extraCheese.value)
        _cart.add(item)
    }

    fun clearCart() {
        _cart.clear()
    }

    fun CartTempItem.toCartItem(): CartItem {
        return CartItem(
            id = this.pizza.id,
            pizza = this.pizza,
            cheese = this.cheese.toInt()
        )
    }

    fun toCartItemList(): List<CartItem> {
        return _cart.map { it.toCartItem() }
    }
}