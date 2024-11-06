package store.data.dto

import store.domain.model.product.ProductRules.PRODUCT_NAME
import store.domain.model.product.ProductRules.PRODUCT_PRICE
import store.domain.model.product.ProductRules.PRODUCT_QUANTITY
import store.domain.model.product.ProductRules.PRODUCT_PROMOTION
import store.domain.model.CommonRules.OUT_OF_STOCK
import store.domain.model.CommonRules.STOCK_UNIT
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
    val name = this[PRODUCT_NAME.getIndex()]

    val price = this[PRODUCT_PRICE.getIndex()].toInt()

    val quantity = if (this[PRODUCT_QUANTITY.getIndex()] == "0") OUT_OF_STOCK.toString()
    else this[PRODUCT_QUANTITY.getIndex()] + STOCK_UNIT.toString()

    val promotion = if (this[PRODUCT_PROMOTION.getIndex()] == "null") ""
    else this[PRODUCT_PROMOTION.getIndex()]

    return ProductResponse(name, price, quantity, promotion)
}
