package store.domain.model.product

import store.domain.model.output.OutputRules.Companion.productFormat

data class Products(
    val items: List<ProductItem>
) {
    fun joinToLineBreak() = items.joinToString("\n") { toUiModel(it) }

    private fun toUiModel(product: ProductItem) = productFormat(product)
}

data class ProductItem(
    val name: String,
    val price: String,
    val quantity: String,
    val promotion: String?
)