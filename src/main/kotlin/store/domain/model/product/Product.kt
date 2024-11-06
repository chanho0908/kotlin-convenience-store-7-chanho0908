package store.domain.model.product

data class Products(
    val products: List<Product>
)

data class Product (
    val name: String,
    val price: Int,
    val quantity: Int,
    val promotion: String
)