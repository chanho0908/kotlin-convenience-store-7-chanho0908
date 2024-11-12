package store.domain.usecase

import store.domain.ext.extractProductName
import store.domain.ext.extractProductQuantity
import store.domain.ext.splitByComma
import store.domain.ext.splitByHyphen

class ExtractOrdersUseCase {
    operator fun invoke(orderList: String): List<Pair<String, Int>> {
        return orderList.splitByComma().map {
            val splitOrder = it.splitByHyphen()
            extractOrder(splitOrder[0], splitOrder[1])
        }
    }

    private fun extractOrder(productName: String, productQuantity: String): Pair<String, Int> {
        val name = productName.extractProductName()
        val quantity = productQuantity.extractProductQuantity().toInt()
        return Pair(name, quantity)
    }
}