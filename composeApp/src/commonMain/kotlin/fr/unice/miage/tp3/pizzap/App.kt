package fr.unice.miage.tp3.pizzap

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import fr.unice.miage.tp3.pizzap.screens.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {

    MaterialTheme {
        Navigator(HomeScreen)
    }
}