package store.domain.model.product

enum class ProductRules(private val idx: Int){
    PRODUCT_NAME(0),
    PRODUCT_PRICE(1),
    PRODUCT_QUANTITY(2),
    PRODUCT_PROMOTION(3);

    fun getIndex(): Int = idx
}