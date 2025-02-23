package fr.unice.miage.tp3.pizzap.database

import fr.unice.miage.tp3.pizzap.dao.OrderDaoDesktop
import fr.unice.miage.tp3.pizzap.datasource.DataSource

object DataSourceFactoryDesktop {
    fun create(): DataSource {
        val orderDao = OrderDaoDesktop()
        return DataSource(orderDao)
    }
}
