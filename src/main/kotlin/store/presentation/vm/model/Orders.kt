package store.presentation.vm.model

import store.domain.model.promotion.PromotionItem

data class Orders(
    val items: List<Order>
)

data class Order (
    val name: String,
    val quantity: Int,
    val promotion: PromotionItem?
)