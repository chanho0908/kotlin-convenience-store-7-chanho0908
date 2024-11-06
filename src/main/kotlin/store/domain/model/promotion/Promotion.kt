package store.domain.model.promotion

data class Promotions (
    val items: List<Item>
)

data class Item (
    val name: String,
    val buy: Int,
    val get: Int,
    val startDate: String,
    val endDate: String,
)