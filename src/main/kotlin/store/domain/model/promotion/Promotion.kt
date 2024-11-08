package store.domain.model.promotion

data class Promotions(
    val items: List<PromotionItem>
) {
    fun filterPromotion(promotionName: String): List<PromotionItem> {
        return items.filter { it.name == promotionName }
    }
}

data class PromotionItem(
    val name: String,
    val buy: Int,
    val get: Int,
    val startDate: String,
    val endDate: String,
)