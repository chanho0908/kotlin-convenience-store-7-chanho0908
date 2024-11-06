package store.data.dto

import store.domain.model.product.ProductRules.PRODUCT_NAME
import store.domain.model.product.ProductRules.PRODUCT_PRICE
import store.domain.model.product.ProductRules.PRODUCT_QUANTITY
import store.domain.model.product.ProductRules.PRODUCT_PROMOTION
import store.domain.model.CommonRules.OUT_OF_STOCK
import store.domain.model.product.Product
import store.domain.model.product.Products

data class ProductResponse(
    val name: String,
    val price: Int,
    val quantity: Int,
    val promotion: String?
) {
    fun toDomainModel(): Product {
        return Product(
            name = name,
            price = price,
            quantity = quantity,
            promotion = promotion ?: OUT_OF_STOCK.getValue()
        )
    }
}

fun List<String>.toProductResponse(): ProductResponse {
    val name = PRODUCT_NAME.getIndex()
    val price = PRODUCT_PRICE.getIndex()
    val quantity = PRODUCT_QUANTITY.getIndex()
    val promotion = PRODUCT_PROMOTION.getIndex()

    return ProductResponse(
        name = this[name],
        price = this[price].toInt(),
        quantity = this[quantity].toInt(),
        promotion = if (this[promotion] == "null") null else this[promotion]
    )
}
