package store.domain.model.receipt

data class Receipt (
    val productReceipt: String,
    val promotionRecipe: String,
    val totalAmount: String,
    val eventDiscount: String,
    val payment: String
)