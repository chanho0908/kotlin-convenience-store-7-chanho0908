package store.data.datasource

import store.data.dto.toPromotionResponse
import store.domain.ext.splitByComma
import java.io.File

class PromotionDataSource {
    private val baseUrl = "src/main/resources/promotions.md"

    fun getPromotion() = File(baseUrl).useLines { lines ->
        processLines(lines)
    }

    private fun processLines(lines: Sequence<String>) =
        lines.drop(1)
            .map { it.splitByComma().toPromotionResponse() }
            .toList()
}