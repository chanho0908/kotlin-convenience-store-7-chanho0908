package store.domain.model

import store.domain.ext.splitByComma
import store.domain.ext.splitByHyphen
import store.domain.model.Constants.COMMA
import store.domain.model.output.OutputRules
import store.domain.model.product.Products
import store.domain.model.promotion.Promotions
import store.domain.model.Constants.SQUARE_BRACKETS_LEFT
import store.domain.model.Constants.SQUARE_BRACKETS_RIGHT
import store.domain.model.output.OutputRules.STOCK_UNIT
import store.domain.model.Exception.NOT_SALES

class Purchase(val input: String, val products: Products, val promotions: Promotions) {
    init {
        require(input.isNotEmpty()) { Exception.INVALID_INPUT }
        require(input.matches(REGEX)) { Exception.INVALID_INPUT_FORMAT }
        checkOtherValidation()
    }

    private fun checkOtherValidation() {
        if (input.contains("$COMMA")) {
            input.splitByComma().forEach {
                checkOrder(it)
            }
        } else {
            checkOrder(input)
        }
    }

    private fun checkOrder(order: String) {
        checkSquareBrackets(order)
        checkDelimiter(order)
        val name = extractProductName(order)
        val quantity = extractProductQuantity(order)
        validQuantity(quantity)
        hasProduct(name)
        outOfStock(name)
        notEnoughStock(name, quantity.toInt())
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

    private fun hasProduct(order: String) {
        require(products.items.any { it.name == order }) { NOT_SALES }
    }

    private fun validQuantity(productQuantity: String) {
        require(productQuantity.toIntOrNull() != null) { Exception.INVALID_INPUT }
    }

    private fun outOfStock(name: String) {
        val stockQuantity = getStockQuantity(name)
        val notOutOfStock = stockQuantity.any { it != "${OutputRules.OUT_OF_STOCK}" }
        require(notOutOfStock) { Exception.NOT_ENOUGH_STOCK }
    }

    private fun notEnoughStock(name: String, quantity: Int) {
        val stockQuantity = getNotOutOfStockQuantity(name)
        require(stockQuantity >= quantity) { Exception.NOT_ENOUGH_STOCK }
    }

    private fun getStockQuantity(name: String): List<String> {
        return products.items.filter { it.name == name }.map { it.quantity }
    }

    private fun getNotOutOfStockQuantity(name: String): Int {
        return products.items
            .filter { it.name == name && it.quantity != "${OutputRules.OUT_OF_STOCK}" }
            .map { it.quantity.removeSuffix("$STOCK_UNIT") }
            .sumOf { it.toInt() }
    }

    private fun extractProductName(order: String): String {
        val productName = order.splitByHyphen().first()
        return productName.trim().removePrefix("$SQUARE_BRACKETS_LEFT")
    }

    private fun extractProductQuantity(order: String): String {
        val productQuantity = order.splitByHyphen().last()
        return productQuantity.trim().removeSuffix("$SQUARE_BRACKETS_RIGHT")
    }

    companion object {
        private val REGEX = Regex("^[가-힣a-zA-Z0-9,\\-\\[\\]]+$")
        private const val SEPARATED_SIZE = 2
    }
}
