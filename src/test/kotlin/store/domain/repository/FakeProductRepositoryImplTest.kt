package store.domain.repository

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
import store.domain.usecase.CheckOrderValidationUseCase
import java.util.stream.Stream
import store.domain.model.Exception.INVALID_INPUT_FORMAT

@TestInstance(Lifecycle.PER_CLASS)
class FakeProductRepositoryImplTest {
    private lateinit var products: Products
    private lateinit var promotions: Promotions
    private lateinit var checkOrderValidationUseCase: CheckOrderValidationUseCase

    @BeforeEach
    fun setUp() {
        products = FakeProductRepositoryImpl().getProduct()
        promotions = FakePromotionRepositoryImpl().getPromotion()
        checkOrderValidationUseCase = CheckOrderValidationUseCase()
    }

    @ParameterizedTest
    @MethodSource("사용자_구매_상품_입력시_숫자_한영_쉼표_대괄호_외_입력시_에러_데이터")
    fun `잘못된 입력값 예외 테스트`(input: String) {
        Assertions.assertThatThrownBy {
            checkOrderValidationUseCase(input, products)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("$INVALID_INPUT_FORMAT")
    }

    companion object {

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
