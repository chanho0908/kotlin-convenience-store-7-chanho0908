package store.domain.model.promotion

data class Promotions (
    val promotions: List<Promotion>
)

data class Promotion (
    val name: String,
    val buy: Int,
    val get: Int,
    val startDate: String,
    val endDate: String,
)