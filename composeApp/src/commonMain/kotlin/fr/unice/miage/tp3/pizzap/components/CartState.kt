package fr.unice.miage.tp3.pizzap.components

import androidx.compose.runtime.MutableState
import fr.unice.miage.tp3.pizzap.model.CartItem
import fr.unice.miage.tp3.pizzap.model.Pizza

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map


data class CartTempItem(
    var pizza: Pizza,
    var cheese: Float
)

object CartState {
    private val _cart = MutableStateFlow<List<CartTempItem>>(emptyList())
    val cart: StateFlow<List<CartTempItem>> get() = _cart

    fun addToCart(pizza: Pizza, extraCheese: MutableState<Float>) {
        val item = CartTempItem(pizza, extraCheese.value)
        _cart.value = _cart.value + item
        // Log the added item and the current state of the cart
        println("CartState: Added item: $item")
        println("CartState: Current cart items: ${_cart.value}")
    }

    fun clearCart() {
        _cart.value = emptyList()
        println("CartState: Cart cleared")
    }

    fun CartTempItem.toCartItem(): CartItem {
        return CartItem(
            id = this.pizza.id,
            pizza = this.pizza,
            cheese = this.cheese.toInt()
        )
    }

    fun toCartItemList() = cart.map { list ->
        list.map { it.toCartItem() }
    }
}
