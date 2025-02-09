package fr.unice.miage.tp3.pizzap.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import fr.unice.miage.tp3.pizzap.components.CartState
import fr.unice.miage.tp3.pizzap.components.PizzaCard
import fr.unice.miage.tp3.pizzap.datasource.DataSource


object MenuScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val pizzas = DataSource().loadPizzas()


        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Menu") },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(pizzas) { pizza ->
                    PizzaCard(
                        pizza = pizza,
                        modifier = Modifier.padding(16.dp),
                        onClickPizza = {
                            navigator?.push(PizzaScreen(pizza, onAddToCart = { CartState.addToCart(pizza) }))
                        },
                        onAddToCart = { CartState.addToCart(pizza) }
                    )
                }
            }
        }
    }
}