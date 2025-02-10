package fr.unice.miage.tp3.pizzap.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: Int,
    var pizza: Pizza,
    val cheese: Int
)