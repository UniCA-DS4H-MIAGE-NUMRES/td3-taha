package fr.unice.miage.tp3.pizzap.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import fr.unice.miage.tp3.pizzap.components.OrderCard
import fr.unice.miage.tp3.pizzap.datasource.DataSourceFactory
import fr.unice.miage.tp3.pizzap.model.Order
import kotlinx.coroutines.launch

object OrderHistoryScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val orderRepository = remember { DataSourceFactory.getOrderRepository() }
        val orderHistory = remember { mutableStateOf<List<Order>>(emptyList()) }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            coroutineScope.launch {
                orderRepository.getOrders().collect { orders ->
                    orderHistory.value = orders
                }
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Order History") },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(orderHistory.value) { order ->
                    OrderCard(order)
                }
            }
        }
    }
}
