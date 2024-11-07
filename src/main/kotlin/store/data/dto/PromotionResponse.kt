package store.data.dto

import store.domain.model.promotion.PromotionItem
import store.domain.model.promotion.PromotionRules.PROMOTION_NAME
import store.domain.model.promotion.PromotionRules.PROMOTION_BUY
import store.domain.model.promotion.PromotionRules.PROMOTION_GET
import store.domain.model.promotion.PromotionRules.PROMOTION_START_DATE
import store.domain.model.promotion.PromotionRules.PROMOTION_END_DATE

data class PromotionResponse(
    val name: String,
    val buy: Int,
    val get: Int,
    val startDate: String,
    val endDate: String,
){
    fun toDomainModel(): PromotionItem {
        return PromotionItem(
            name = name,
            buy = buy,
            get = get,
            startDate = startDate,
            endDate = endDate
        )
    }
}

fun List<String>.toPromotionResponse(): PromotionResponse {
    val name = PROMOTION_NAME.getIndex()
    val buy = PROMOTION_BUY.getIndex()
    val get = PROMOTION_GET.getIndex()
    val startDate = PROMOTION_START_DATE.getIndex()
    val endDate = PROMOTION_END_DATE.getIndex()

    return PromotionResponse(
        name = this[name],
        buy = this[buy].toInt(),
        get = this[get].toInt(),
        startDate = this[startDate],
        endDate = this[endDate]
    )
}
