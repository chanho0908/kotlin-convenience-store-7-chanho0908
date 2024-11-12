package store.data.repository

import store.domain.model.product.ProductItem
import store.domain.model.product.Products
import store.domain.repository.ProductRepository

class FakeProductRepositoryImpl : ProductRepository {
    override fun getProduct(): Products {
        val items = listOf(
            ProductItem("콜라", "1,000", "10개", "탄산2+1"),
            ProductItem("콜라", "1,000", "재고 없음", ""),
            ProductItem("사이다", "1,000", "8개", "탄산2+1"),
            ProductItem("물", "1,000", "10개", ""),
            ProductItem("오렌지주스", "1,000", "10개", "")
        )
        return Products(items)
    }
}
