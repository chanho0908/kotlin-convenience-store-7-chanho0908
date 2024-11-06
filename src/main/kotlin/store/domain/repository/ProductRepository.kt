package store.domain.repository

import store.domain.model.product.Products

interface ProductRepository {
    fun getProduct(): Products
}