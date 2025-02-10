package fr.unice.miage.tp3.pizzap.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.unice.miage.tp3.pizzap.model.Order
import androidx.compose.material.Text
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import fr.unice.miage.tp3.pizzap.model.CartItem
import kotlin.math.round


@Composable
fun OrderCard(order: Order) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFFFF8E1))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Date : ${order.timestamp}", color = Color(0xFF1E8560))
                    Text("Total : ${order.totalPrice}€", color = Color.Black)
                }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand Order",
                        tint = Color(0xFFA0522D)
                    )
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                order.items.forEach { item ->
                    OrderItemView(item = item)
                }
            }

        }
    }
}
@Composable
fun OrderItemView(item: CartItem) {
    val cheesePrice = item.cheese * 0.01
    val totalPrice = item.pizza.price + cheesePrice

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFFFF8E1))
            .padding(8.dp)
    ) {
        Text(item.pizza.name, color = Color(0xFF1E8560))
        Text(
            if (item.cheese > 0)
                "Fromage supplémentaire : ${item.cheese} g (${round(cheesePrice * 100) / 100}€)"
            else
                "Pas de fromage supplémentaire",
            color = Color(0xFFA0522D)
        )
        Text("Prix total : ${round(totalPrice * 100) / 100}€", color = Color.Black)
    }
}
