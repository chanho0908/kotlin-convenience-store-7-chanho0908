package store.domain.model.promotion

enum class PromotionRules(private val idx: Int){
    PROMOTION_NAME(0),
    PROMOTION_BUY(1),
    PROMOTION_GET(2),
    PROMOTION_START_DATE(3),
    PROMOTION_END_DATE(4);

    fun getIndex(): Int = idx
}