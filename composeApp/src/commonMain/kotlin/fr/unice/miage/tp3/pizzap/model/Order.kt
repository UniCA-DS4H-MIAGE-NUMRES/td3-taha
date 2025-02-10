package fr.unice.miage.tp3.pizzap.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Int,
    val timestamp: String,
    val items: List<CartItem>,
    val totalPrice: Double
)