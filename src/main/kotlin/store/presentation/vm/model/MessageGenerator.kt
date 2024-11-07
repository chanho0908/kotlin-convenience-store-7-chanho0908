package store.presentation.vm.model

import store.domain.model.output.OutputRules
import store.domain.model.product.Products

class MessageGenerator {
    fun makeCurrentStockGuideMessage(products: Products): String{
        return "${products.joinToLineBreak()}\n\n${OutputRules.GUIDE}"
    }
}