package store.domain.usecase

import store.domain.repository.PromotionRepository
import camp.nextstep.edu.missionutils.DateTimes
import store.domain.ext.toLocalDate
import store.domain.model.promotion.PromotionItem
import java.time.LocalDateTime

class GetUnexpiredPromotionUseCase(
    private val promotionRepository: PromotionRepository
) {
    operator fun invoke(promotionName: String): PromotionItem? {
        return unExpiredPromotion(promotionName)
    }

    private fun unExpiredPromotion(promotionName: String): PromotionItem? {
        val foundPromotion = getPromotion(promotionName)
        if (foundPromotion != null &&
            foundPromotion.startDate.toLocalDate() <= today().toLocalDate() &&
            foundPromotion.endDate.toLocalDate() >= today().toLocalDate())
        {
            return foundPromotion
        }
        return null
    }

    private fun getPromotion(promotionName: String): PromotionItem? {
        return promotionRepository.getPromotion().filterPromotion(promotionName)
    }

    private fun today(): LocalDateTime = DateTimes.now()
}