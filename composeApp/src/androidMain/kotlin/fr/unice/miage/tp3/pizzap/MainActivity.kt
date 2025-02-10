package fr.unice.miage.tp3.pizzap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.unice.miage.tp3.pizzap.dao.AndroidOrderDaoImpl
import fr.unice.miage.tp3.pizzap.dao.OrderDao
import fr.unice.miage.tp3.pizzap.database.PizzaDatabase
import fr.unice.miage.tp3.pizzap.datasource.DataSourceFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation de la base de données
        val database = PizzaDatabase.getDatabase(this)
        val androidOrderDao = database.orderDao()

        val orderDao: OrderDao = AndroidOrderDaoImpl(androidOrderDao)

        // Initialisation de DataSourceFactory avec OrderDao
        DataSourceFactory.init(orderDao)

        setContent {
            App()
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}