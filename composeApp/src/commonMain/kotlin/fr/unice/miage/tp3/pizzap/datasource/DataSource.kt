package fr.unice.miage.tp3.pizzap.datasource

import fr.unice.miage.tp3.pizzap.model.Pizza
import pizzapp.composeapp.generated.resources.*

class DataSource() {
    fun loadPizzas() : List<Pizza>{
        return listOf(
            Pizza("Margherita", 8.0, Res.drawable.pizza1),
            Pizza("Capricciosa", 10.0, Res.drawable.pizza2),
            Pizza("Diavola", 9.0,Res.drawable.pizza3),
            Pizza("Quattro Stagioni", 11.0, Res.drawable.pizza4),
            Pizza("Quattro Formaggi", 12.0, Res.drawable.pizza5),
            Pizza("Marinara", 7.0, Res.drawable.pizza6),
            Pizza("Pepperoni", 9.0, Res.drawable.pizza7),
            Pizza("Prosciutto", 10.0, Res.drawable.pizza8),
            Pizza("Frutti di Mare", 13.0, Res.drawable.pizza9)
        )
    }


    fun loadPizza(id: Int): Pizza {
        return loadPizzas()[id]
    }
}