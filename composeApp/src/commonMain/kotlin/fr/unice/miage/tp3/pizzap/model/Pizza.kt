package fr.unice.miage.tp3.pizzap.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

@Serializable
data class Pizza(
    val id: Int,
    val name: String,
    val price: Double,
    val imagePath: String
)