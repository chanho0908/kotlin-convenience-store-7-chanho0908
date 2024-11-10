package store.presentation.view

import store.domain.model.output.OutputRules

class OutputView {
    fun printMessage(message: String) {
        println(message)
    }

    fun printReceiptHeader() {
        println("\n${OutputRules.RECIPE_CATEGORY}")
    }

    fun printPromotionReceiptHeader() {
        println("${OutputRules.RECIPE_PROMOTION}")
    }

    fun printDottedLine() {
        println("${OutputRules.DOTTED_LINE}")
    }
}