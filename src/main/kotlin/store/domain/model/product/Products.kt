package store.domain.model.product

import store.domain.model.output.OutputRules
import store.domain.model.output.OutputRules.Companion.productFormat

data class Products(
    val items: List<ProductItem>
) {
    private fun joinToLineBreak() = items.joinToString("\n") { toUiModel(it) }

    fun makeCurrentStockGuideMessage(): String{
        return "${this.joinToLineBreak()}\n\n${OutputRules.GUIDE}"
    }

    fun hasPromotion(name: String): String?{
        return items.find { it.name == name && it.promotion != null }?.promotion
    }

    private fun toUiModel(product: ProductItem) = productFormat(product)
}

data class ProductItem(
    val name: String,
    val price: String,
    val quantity: String,
    val promotion: String?
)