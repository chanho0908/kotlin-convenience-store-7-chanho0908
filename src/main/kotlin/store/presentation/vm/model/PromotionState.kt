package store.presentation.vm.model

import store.domain.model.promotion.PromotionItem

sealed class PromotionState{
    object NoPromotion: PromotionState()
    data class InProgress(val name: String, val buy: Int, val get: Int): PromotionState()
    object NotInProgress: PromotionState()

    companion object{
        fun create(promotion: PromotionItem?): PromotionState{
            return promotion?.toUiModel() ?: NotInProgress
        }
    }
}

fun PromotionItem.toUiModel(): PromotionState.InProgress {
    return PromotionState.InProgress(
        name = this.name,
        buy = this.buy,
        get = this.get
    )
}