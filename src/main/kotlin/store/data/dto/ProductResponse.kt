package store.data.dto

import store.domain.model.product.ProductRules.PRODUCT_NAME
import store.domain.model.product.ProductRules.PRODUCT_PRICE
import store.domain.model.product.ProductRules.PRODUCT_QUANTITY
import store.domain.model.product.ProductRules.PRODUCT_PROMOTION
import store.domain.model.CommonRules.OUT_OF_STOCK
import store.domain.model.product.Item
import java.text.NumberFormat
import java.util.Locale

data class ProductResponse(
    val name: String,
    val price: Int,
    val quantity: String,
    val promotion: String?
) {
    fun toDomainModel(): Item {
        val item = Item(
            name = name,
            price = NumberFormat.getNumberInstance(Locale.KOREA).format(this.price),
            quantity = quantity,
            promotion = promotion
        )
        return item
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
        quantity = if (this[quantity] == "0") OUT_OF_STOCK.toString() else this[quantity],
        promotion = if (this[promotion] == "null") "" else this[promotion]
    )
}
