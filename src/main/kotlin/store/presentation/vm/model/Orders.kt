package store.presentation.vm.model

data class Orders(
    val items: List<Order>
)

data class Order (
    val name: String,
    val quantity: Int,
    val promotion: PromotionState
)