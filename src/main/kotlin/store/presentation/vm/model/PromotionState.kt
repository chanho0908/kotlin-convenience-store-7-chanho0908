package store.presentation.vm.model

import store.domain.model.promotion.PromotionItem

data class PromotionState(
    val name: String,
    val buy: Int,
    val get: Int
)

fun PromotionItem.toUiModel(): PromotionState{
    return PromotionState(
        name = this.name,
        buy = this.buy,
        get = this.get
    )
}