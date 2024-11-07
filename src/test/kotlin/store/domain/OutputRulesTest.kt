package store.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import store.domain.model.output.OutputRules
import store.domain.model.product.ProductItem

class OutputRulesTest {

    @Test
    fun `판매_상품_안내용_텍스트_포맷_변환_테스트`() {

        val actual = OutputRules.productFormat(
            ProductItem(
                name = "콜라",
                price = "1,000",
                quantity = "10",
                promotion = "탄산2+1"
            )
        )

        val expected = "- 콜라 1,000원 10 탄산2+1"

        assertThat(actual).isEqualTo(expected)
    }

}