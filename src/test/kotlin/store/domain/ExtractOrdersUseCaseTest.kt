package store.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import store.domain.usecase.ExtractOrdersUseCase
import store.presentation.vm.model.Order
import java.util.stream.Stream

class ExtractOrdersUseCaseTest {
    private lateinit var extractOrdersUseCase: ExtractOrdersUseCase

    @BeforeEach
    fun setUp() {
        extractOrdersUseCase = ExtractOrdersUseCase()
    }

    @ParameterizedTest
    @MethodSource("사용자_주문을_상품_이름과_수량으로_분리_테스트")
    fun `사용자 주문을 상품 이름과 수량으로 분리`(input: String, expected: List<Order>) {
        val result = extractOrdersUseCase(input)
        assertEquals(result, expected)
    }

    companion object {
        @JvmStatic
        fun `사용자_주문을_상품_이름과_수량으로_분리_테스트`() = Stream.of(
            Arguments.of(
                "[콜라-1],[사이다-1]", listOf(
                    Order("콜라", 1),
                    Order("사이다", 1)
                )
            ),
            Arguments.of(
                "[물-3],[커피-2],[치킨-20]", listOf(
                    Order("물", 3),
                    Order("커피", 2),
                    Order("치킨", 20)
                )
            ),
            Arguments.of(
                "[주스-5],[우유-4],[요플레-20],[뚜러뻥-1]", listOf(
                    Order("주스", 5),
                    Order("우유", 4),
                    Order("요플레", 20),
                    Order("뚜러뻥", 1)
                )
            ),
            Arguments.of(
                "[주스-5],[우유-4],[요플레-20],[뚜러뻥-1],[불닭 볶음면-2]", listOf(
                    Order("주스", 5),
                    Order("우유", 4),
                    Order("요플레", 20),
                    Order("뚜러뻥", 1),
                    Order("불닭 볶음면", 2)
                )
            )
        )
    }
}