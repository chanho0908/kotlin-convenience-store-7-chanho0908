package store.data.repository

import store.data.datasource.PromotionDataSource
import store.domain.model.promotion.Promotions
import store.domain.repository.PromotionRepository

class PromotionRepositoryImpl(
    private val dataSource: PromotionDataSource
): PromotionRepository {
    override fun getPromotion(): Promotions {
        return Promotions(dataSource.getPromotion().map { it.toDomainModel() })
    }
}