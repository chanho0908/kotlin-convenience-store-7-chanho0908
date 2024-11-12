package store.domain.repository

import store.domain.model.promotion.Promotions

interface PromotionRepository {
    fun getPromotion(): Promotions
}