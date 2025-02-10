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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import fr.unice.miage.tp3.pizzap.components.CartState
import fr.unice.miage.tp3.pizzap.components.CartTempItem
import fr.unice.miage.tp3.pizzap.components.PizzaCard
import fr.unice.miage.tp3.pizzap.datasource.DataSourceFactory


object MenuScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val dataSource = remember { DataSourceFactory.getInstance() }
        val pizzas = dataSource.getPizzas()
        val extraCheese = remember { mutableStateOf(0f) }


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
                            navigator?.push(PizzaScreen(pizza, onAddToCart = { CartState.addToCart(pizza, extraCheese)}))
                        },
                        onAddToCart = { CartState.addToCart(
                            pizza,
                            extraCheese
                        ) }
                    )
                }
            }
        }
    }
}