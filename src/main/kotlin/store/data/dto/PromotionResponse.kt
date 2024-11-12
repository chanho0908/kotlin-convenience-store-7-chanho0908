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
        return PromotionItem(name, buy, get, startDate, endDate)
    }
}

fun List<String>.toPromotionResponse(): PromotionResponse {
    val name = this[PROMOTION_NAME.getIndex()]
    val buy = this[PROMOTION_BUY.getIndex()].toInt()
    val get = this[PROMOTION_GET.getIndex()].toInt()
    val startDate = this[PROMOTION_START_DATE.getIndex()]
    val endDate = this[PROMOTION_END_DATE.getIndex()]

    return PromotionResponse(name, buy, get, startDate, endDate)
}
