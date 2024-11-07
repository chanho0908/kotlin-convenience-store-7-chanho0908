package store.data.repository

import store.domain.model.promotion.PromotionItem
import store.domain.model.promotion.Promotions
import store.domain.repository.PromotionRepository

class FakePromotionRepositoryImpl: PromotionRepository {
    override fun getPromotion(): Promotions {
        val items = listOf(
            PromotionItem("탄산2+1", 2, 1, "2024-01-01", "2024-12-31"),
            PromotionItem("MD추천상품", 1, 1, "2024-01-01", "2024-12-31"),
            PromotionItem("반짝할인", 1, 1, "2024-11-01", "2024-11-30"),
        )

        return Promotions(items)
    }
}