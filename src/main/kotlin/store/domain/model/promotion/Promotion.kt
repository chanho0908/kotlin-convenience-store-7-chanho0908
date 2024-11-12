package store.domain.model.promotion

data class Promotions(
    val items: List<PromotionItem>
) {
    fun filterPromotion(promotionName: String): PromotionItem? {
        return items.find { it.name == promotionName }
    }
}

data class PromotionItem(
    val name: String,
    val buy: Int,
    val get: Int,
    val startDate: String,
    val endDate: String,
)