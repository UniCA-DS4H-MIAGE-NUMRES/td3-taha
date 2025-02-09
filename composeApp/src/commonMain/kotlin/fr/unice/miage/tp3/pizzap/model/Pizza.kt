package fr.unice.miage.tp3.pizzap.model

import org.jetbrains.compose.resources.DrawableResource

data class Pizza(
    val name: String,
    val price: Double,
    val imagePath: DrawableResource
)