package fr.unice.miage.tp3.pizzap.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import fr.unice.miage.tp3.pizzap.components.CartState
import fr.unice.miage.tp3.pizzap.datasource.DataSourceFactory
import fr.unice.miage.tp3.pizzap.model.CartItem
import fr.unice.miage.tp3.pizzap.model.Order
import fr.unice.miage.tp3.pizzap.repository.OrderRepository
import kotlinx.coroutines.launch

object CartScreen : Screen {
    private fun getCurrentDate(): String {
        return "2025-12-31"
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val coroutineScope = rememberCoroutineScope()
        val orderRepository = remember { DataSourceFactory.getOrderRepository() }

        // Collect the flow of CartItem lists; initial value is an empty list
        val cartItems by CartState.toCartItemList().collectAsState(initial = emptyList())

        // Derive the total price from the collected cart items
        val totalPrice by derivedStateOf {
            cartItems.sumOf { item ->
                item.pizza.price + (item.cheese * 0.1)
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Cart") },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text(text = "Votre commande", style = MaterialTheme.typography.h3)
                cartItems.forEach { cartItem ->
                    Text(
                        text = "${cartItem.pizza.name} - ${cartItem.pizza.price}€",
                        style = MaterialTheme.typography.body1
                    )
                }
                Text(text = "Total: $totalPrice€", style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // Log the current cart items before creating the order
                            println("CartScreen: Validating cart. Cart items: $cartItems")

                            val order = Order(
                                0,
                                getCurrentDate(),
                                cartItems,
                                totalPrice
                            )
                            CartState.clearCart()
                            // Log the order details
                            println("CartScreen: Persisting order: $order")
                            orderRepository.addOrder(order)
                            navigator?.pop()
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Valider la commande")
                }
            }
        }
    }
}
