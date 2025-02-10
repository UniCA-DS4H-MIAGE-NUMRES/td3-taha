package fr.unice.miage.tp3.pizzap.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import fr.unice.miage.tp3.pizzap.components.CartState
import fr.unice.miage.tp3.pizzap.datasource.DataSourceFactory
import fr.unice.miage.tp3.pizzap.model.CartItem
import fr.unice.miage.tp3.pizzap.model.Pizza
import org.jetbrains.compose.resources.painterResource
import pizzapp.composeapp.generated.resources.Res
import pizzapp.composeapp.generated.resources.logo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val dataSource = remember { DataSourceFactory.getInstance() }
        val pizzas = dataSource.getPizzas()

        var selectedPizza by remember { mutableStateOf<Pizza?>(null) }
        val cartItems = remember { MutableStateFlow<List<CartItem>>(emptyList()) }
        val orderRepository = remember { DataSourceFactory.getOrderRepository() }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "PizzaApp", style = MaterialTheme.typography.h1)
            Text(text = "Da Leo", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navigator?.push(MenuScreen) }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Voir le menu")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navigator?.push(CartScreen) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Voir le panier")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navigator?.push(OrderHistoryScreen) }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Historique des commandes")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { CartState.clearCart() }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Vider le panier")
            }
        }
    }
}
