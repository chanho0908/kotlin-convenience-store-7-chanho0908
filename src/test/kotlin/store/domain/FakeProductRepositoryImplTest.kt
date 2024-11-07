package store.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import store.data.repository.FakeProductRepositoryImpl
import store.data.repository.FakePromotionRepositoryImpl
import store.domain.model.product.Products
import store.domain.model.promotion.Promotions
import store.domain.model.Purchase
import java.util.stream.Stream
import store.domain.model.Exception.INVALID_INPUT_FORMAT
import store.domain.model.output.OutputRules

@TestInstance(Lifecycle.PER_CLASS)
class FakeProductRepositoryImplTest {
    private lateinit var products: Products
    private lateinit var promotions: Promotions

    @BeforeEach
    fun setUp() {
        products = FakeProductRepositoryImpl().getProduct()
        promotions = FakePromotionRepositoryImpl().getPromotion()
    }

    @ParameterizedTest
    @MethodSource("사용자_구매_리스트_정상_데이터")
    fun `유효한 입력값 테스트`(input: String) {
        val purchase = Purchase(input, products, promotions)
        assertEquals(input, purchase.input)
        assertEquals(products, purchase.products)
        assertEquals(promotions, purchase.promotions)
    }

    @ParameterizedTest
    @MethodSource("사용자_구매_상품_입력시_숫자_한영_쉼표_대괄호_외_입력시_에러_데이터")
    fun `잘못된 입력값 예외 테스트`(input: String) {
        Assertions.assertThatThrownBy { Purchase(input, products, promotions)
            }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("$INVALID_INPUT_FORMAT${OutputRules.GUIDE}")
    }

    companion object {
        @JvmStatic
        fun `사용자_구매_리스트_정상_데이터`() = Stream.of(
            "[콜라-3]",
            "[사이다-2],[물-10]",
            "[오렌지주스-1]"
        )

        @JvmStatic
        fun `사용자_구매_상품_입력시_숫자_한영_쉼표_대괄호_외_입력시_에러_데이터`() = Stream.of(
            "[콜라-3]$[에너지바-5]",
            "[콜라-3],[에너지바-5()&",
            "[콜라-3],$,에너지바-5]",
            "[콜라-3]12,$%[에너지바-5]5",
            "[콜라-3]@[에너지바-5]"
        )
    }

}
