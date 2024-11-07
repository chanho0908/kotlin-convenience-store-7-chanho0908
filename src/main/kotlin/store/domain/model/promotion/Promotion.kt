package store.domain.model.promotion

data class Promotions (
    val items: List<PromotionItem>
)

data class PromotionItem (
    val name: String,
    val buy: Int,
    val get: Int,
    val startDate: String,
    val endDate: String,
)