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
import pizzapp.composeapp.generated.resources.Res
import pizzapp.composeapp.generated.resources.*


@Composable
fun PizzaCard(pizza: Pizza, modifier: Modifier = Modifier, onClickPizza: () -> Unit, onAddToCart: () -> Unit) {
    val imageRes = when (pizza.imagePath) {
        "pizza1" -> Res.drawable.pizza1
        "pizza2" -> Res.drawable.pizza2
        "pizza3" -> Res.drawable.pizza3
        "pizza4" -> Res.drawable.pizza4
        "pizza5" -> Res.drawable.pizza5
        "pizza6" -> Res.drawable.pizza6
        "pizza7" -> Res.drawable.pizza7
        "pizza8" -> Res.drawable.pizza8
        "pizza9" -> Res.drawable.pizza9
        else -> Res.drawable.logo
    }

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClickPizza() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
            Text(text = pizza.name, modifier = Modifier.padding(top = 8.dp))
            Text(text = "Price: ${pizza.price}€", modifier = Modifier.padding(top = 4.dp))
        }
    }
}
