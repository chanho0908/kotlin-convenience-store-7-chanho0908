package store.presentation.view

import store.domain.model.output.OutputRules

class OutputView {
    fun printMessage(message: String) {
        println(message)
    }

    fun printReceiptHeader() {
        println(OutputRules.RECIPE_CATEGORY.toString())
    }

    fun printPromotionReceiptHeader() {
        println(OutputRules.RECIPE_PROMOTION.toString())
    }

    fun printDottedLine() {
        println(OutputRules.DOTTED_LINE.toString())
    }
}