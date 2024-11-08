package store.presentation.vm.model

data class Orders(
    val orders: List<Order>
)

data class Order (
    val name: String,
    val quantity: Int
)