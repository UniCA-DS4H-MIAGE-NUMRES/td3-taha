package fr.unice.miage.tp3.pizzap.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import fr.unice.miage.tp3.pizzap.model.Pizza
import org.jetbrains.compose.resources.painterResource

data class PizzaScreen(val pizza: Pizza, val onAddToCart: () -> Unit) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val extraCheese = remember { mutableStateOf(0f) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(pizza.name) },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) {
            paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Centered content
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(pizza.imagePath),
                        contentDescription = "Logo",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = pizza.name, style = MaterialTheme.typography.h4)
                    Text(text = "Price: ${pizza.price}€", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Extra Cheese: ${extraCheese.value.toInt()}%", style = MaterialTheme.typography.body2)
                    Slider(
                        value = extraCheese.value,
                        onValueChange = { extraCheese.value = it },
                        valueRange = 0f..100f,
                        steps = 10,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Button(
                    onClick = onAddToCart,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Text(text = "Add to Cart")
                }
            }
        }
        }
    }