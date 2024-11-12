package store.data.datasource

import store.data.dto.toProductResponse
import store.domain.ext.splitByComma
import java.io.File

class ProductDataSource {
    private val baseUrl = "src/main/resources/products.md"

    fun getProduct() = File(baseUrl).useLines { lines ->
        processLines(lines)
    }

    private fun processLines(lines: Sequence<String>) =
        lines.drop(1)
            .map { it.splitByComma().toProductResponse() }
            .toList()
}
