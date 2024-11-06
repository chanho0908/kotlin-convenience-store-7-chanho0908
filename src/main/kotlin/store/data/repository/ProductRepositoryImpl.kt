package store.data.repository

import store.data.datasource.ProductDataSource
import store.domain.model.product.Products
import store.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository {
    override fun getProduct(): Products {
        return Products(dataSource.getProduct().map { it.toDomainModel() })
    }
}