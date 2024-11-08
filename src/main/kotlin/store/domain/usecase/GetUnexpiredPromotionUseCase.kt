package store.domain.usecase

import store.domain.repository.PromotionRepository
import camp.nextstep.edu.missionutils.DateTimes
import store.domain.ext.toLocalDate
import store.domain.model.promotion.PromotionItem
import store.domain.model.promotion.Promotions
import java.time.LocalDateTime

class GetUnexpiredPromotionUseCase(
    private val promotionRepository: PromotionRepository
) {
    operator fun invoke(promotionName: String): Promotions {
        return Promotions(items = unExpiredPromotion(promotionName))
    }

    private fun unExpiredPromotion(promotionName: String): List<PromotionItem> {
        return getPromotion(promotionName).filter {
            it.startDate.toLocalDate() <= today().toLocalDate() &&
            it.endDate.toLocalDate() >= today().toLocalDate()
        }
    }

    private fun getPromotion(promotionName: String): List<PromotionItem> {
        return promotionRepository.getPromotion()
            .filterPromotion(promotionName)
    }

    private fun today(): LocalDateTime = DateTimes.now()
}