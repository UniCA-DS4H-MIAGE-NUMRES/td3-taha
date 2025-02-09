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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import fr.unice.miage.tp3.pizzap.components.CartState
import org.jetbrains.compose.resources.painterResource
import pizzapp.composeapp.generated.resources.Res
import pizzapp.composeapp.generated.resources.logo

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

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
            Button(onClick = { navigator?.push(MenuScreen) }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Voir le menu")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navigator?.push(CartScreen) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Voir la commande")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { CartState.clearCart() }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Payer la commande")
            }
        }
    }
}
