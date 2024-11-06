package store.domain.model.product

import store.domain.model.output.OutPutRules.Companion.productFormat

data class Products(
    val items: List<Item>
) {
    fun joinToLineBreak() = items.joinToString("\n") { toProductFormat(it) }

    private fun toProductFormat(product: Item) = productFormat(
        name = product.name,
        price = product.price,
        quantity = product.quantity,
        promotion = product.promotion
    )
}

data class Item(
    val name: String,
    val price: String,
    val quantity: String,
    val promotion: String?
)