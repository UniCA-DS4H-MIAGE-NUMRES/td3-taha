package fr.unice.miage.tp3.pizzap.components

import kotlinx.coroutines.flow.MutableStateFlow
import fr.unice.miage.tp3.pizzap.model.CartItem

data class CartHolder(
    val cartItems: MutableStateFlow<List<CartItem>> = MutableStateFlow<List<CartItem>>(emptyList())
) {
    fun addToCart(item: CartItem) {
        cartItems.value = cartItems.value + item
        println("cartHolder: Added item: $item")

    }

    fun clearCart() {
        cartItems.value = emptyList()
        println("cartHolder: Cart cleared")
    }
}
