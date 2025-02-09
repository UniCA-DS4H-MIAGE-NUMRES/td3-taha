package fr.unice.miage.tp3.pizzap.components

import androidx.compose.runtime.mutableStateListOf
import fr.unice.miage.tp3.pizzap.model.Pizza

object CartState {
    private val _cart = mutableStateListOf<Pizza>()
    val cart: List<Pizza> get() = _cart

    fun addToCart(pizza: Pizza) {
        _cart.add(pizza)
    }

    fun clearCart() {
        _cart.clear()
    }
}