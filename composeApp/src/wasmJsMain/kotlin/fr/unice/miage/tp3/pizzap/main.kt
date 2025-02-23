package fr.unice.miage.tp3.pizzap

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import fr.unice.miage.tp3.pizzap.Dao.WasmOrderDao
import fr.unice.miage.tp3.pizzap.datasource.DataSourceFactory
import kotlinx.browser.document


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val orderDao = WasmOrderDao()
    DataSourceFactory.init(orderDao)
    ComposeViewport(document.body!!) { App() }
}