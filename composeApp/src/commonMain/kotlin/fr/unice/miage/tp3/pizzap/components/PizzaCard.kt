package fr.unice.miage.tp3.pizzap.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fr.unice.miage.tp3.pizzap.model.Pizza
import org.jetbrains.compose.resources.painterResource


@Composable
fun PizzaCard(pizza: Pizza, modifier: Modifier = Modifier, onClickPizza: () -> Unit, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClickPizza() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(pizza.imagePath),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
            Text(text = pizza.name, modifier = Modifier.padding(top = 8.dp))
            Text(text = "Price: ${pizza.price}€", modifier = Modifier.padding(top = 4.dp))
        }
    }
}
