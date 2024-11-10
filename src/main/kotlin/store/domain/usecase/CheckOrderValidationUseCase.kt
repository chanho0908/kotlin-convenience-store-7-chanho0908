package store.domain.usecase

import store.domain.ext.extractProductName
import store.domain.ext.extractProductQuantity
import store.domain.ext.removeStockUnitSuffix
import store.domain.ext.splitByComma
import store.domain.ext.splitByHyphen
import store.domain.model.Constants.COMMA
import store.domain.model.output.OutputRules
import store.domain.model.product.Products
import store.domain.model.Constants.SQUARE_BRACKETS_LEFT
import store.domain.model.Constants.SQUARE_BRACKETS_RIGHT
import store.domain.model.Exception
import store.domain.model.Exception.NOT_SALES

class CheckOrderValidationUseCase {
    operator fun invoke(order: String, products: Products) {
        require(order.isNotEmpty()) { Exception.INVALID_INPUT }
        require(order.matches(REGEX)) { Exception.INVALID_INPUT_FORMAT }
        checkOtherValidation(order, products)
    }

    private fun checkOtherValidation(order: String, products: Products) {
        if (order.contains("$COMMA")) {
            order.splitByComma().forEach {
                checkOrder(it, products)
            }
        } else {
            checkOrder(order, products)
        }
    }

    private fun checkOrder(order: String, products: Products) {
        checkSquareBrackets(order)
        checkDelimiter(order)
        val name = order.extractProductName()
        val quantity = order.extractProductQuantity()
        validQuantity(quantity)
        hasProduct(name, products)
        outOfStock(name, products)
        notEnoughStock(name, quantity.toInt(), products)
    }

    private fun checkSquareBrackets(order: String) {
        val startsWithSquareBrackets = order.startsWith("$SQUARE_BRACKETS_LEFT")
        val endsWithSquareBrackets = order.endsWith("$SQUARE_BRACKETS_RIGHT")
        require(startsWithSquareBrackets && endsWithSquareBrackets) {
            Exception.INVALID_INPUT_FORMAT
        }
    }

    private fun checkDelimiter(order: String) {
        val separatedOrder = order.splitByHyphen()
        require(separatedOrder.size == SEPARATED_SIZE) {
            Exception.INVALID_INPUT_FORMAT
        }
    }

    private fun hasProduct(order: String, products: Products) {
        require(products.items.any { it.name == order }) { NOT_SALES }
    }

    private fun validQuantity(productQuantity: String) {
        require(productQuantity.toIntOrNull() != null) { Exception.INVALID_INPUT }
    }

    private fun outOfStock(name: String, products: Products) {
        val stockQuantity = getStockQuantity(name, products)
        val notOutOfStock = stockQuantity.any { it != "${OutputRules.OUT_OF_STOCK}" }
        require(notOutOfStock) { Exception.NOT_ENOUGH_STOCK }
    }

    private fun notEnoughStock(name: String, quantity: Int, products: Products) {
        val stockQuantity = getNotOutOfStockQuantity(name, products)
        require(stockQuantity >= quantity) { Exception.NOT_ENOUGH_STOCK }
    }

    private fun getStockQuantity(name: String, products: Products): List<String> {
        return products.items.filter { it.name == name }.map { it.quantity }
    }

    private fun getNotOutOfStockQuantity(name: String, products: Products): Int {
        return products.items
            .filter { it.name == name && it.quantity != "${OutputRules.OUT_OF_STOCK}" }
            .map { it.quantity.removeStockUnitSuffix() }
            .sumOf { it.toInt() }
    }

    companion object {
        private val REGEX = Regex("^[가-힣a-zA-Z0-9,\\-\\[\\]]+$")
        private const val SEPARATED_SIZE = 2
    }
}
