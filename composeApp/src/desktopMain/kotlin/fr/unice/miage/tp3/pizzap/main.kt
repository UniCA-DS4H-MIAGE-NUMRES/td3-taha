package fr.unice.miage.tp3.pizzap

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fr.unice.miage.tp3.pizzap.dao.OrderDaoDesktop
import fr.unice.miage.tp3.pizzap.datasource.DataSourceFactory

fun main() = application {
    DataSourceFactory.init(OrderDaoDesktop())

    Window(
        onCloseRequest = ::exitApplication,
        title = "PizzApp",
    ) {
        App()
    }
}