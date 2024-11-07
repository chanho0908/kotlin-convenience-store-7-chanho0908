package store.domain.model.product

import store.domain.model.output.OutputRules.Companion.productFormat

data class Products(
    val items: List<Item>
) {
    fun joinToLineBreak() = items.joinToString("\n") { toUiModel(it) }

    private fun toUiModel(product: Item) = productFormat(product)
}

data class Item(
    val name: String,
    val price: String,
    val quantity: String,
    val promotion: String?
)