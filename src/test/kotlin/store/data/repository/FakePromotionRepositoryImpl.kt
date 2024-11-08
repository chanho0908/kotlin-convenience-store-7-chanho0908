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
            PromotionItem("사장님이미쳤어요", 1, 1, "2025-11-01", "2026-11-30"),
            PromotionItem("무료반품", 1, 1, "2023-11-01", "2023-11-30"),
        )

        return Promotions(items)
    }
}