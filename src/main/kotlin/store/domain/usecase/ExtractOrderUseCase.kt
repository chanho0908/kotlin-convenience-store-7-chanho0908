package store.domain.usecase

import store.domain.ext.extractProductName
import store.domain.ext.extractProductQuantity
import store.domain.ext.splitByComma
import store.domain.ext.splitByHyphen
import store.presentation.vm.model.Order

class ExtractOrderUseCase {
    operator fun invoke(orderList: String): List<Order> {
        return orderList.splitByComma().map {
            val splitOrder = it.splitByHyphen()
            extractOrder(splitOrder[0], splitOrder[1])
        }
    }

    private fun extractOrder(productName: String, productQuantity: String): Order {
        val name = productName.extractProductName()
        val quantity = productQuantity.extractProductQuantity().toInt()
        return Order(name, quantity)
    }
}