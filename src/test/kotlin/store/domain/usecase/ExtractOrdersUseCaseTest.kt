package store.domain.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
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
                    Pair("콜라", 1, ),
                    Pair("사이다", 1)
                )
            ),
            Arguments.of(
                "[물-3],[커피-2],[치킨-20]", listOf(
                    Pair("물", 3),
                    Pair("커피", 2),
                    Pair("치킨", 20)
                )
            ),
            Arguments.of(
                "[주스-5],[우유-4],[요플레-20],[뚜러뻥-1]", listOf(
                    Pair("주스", 5),
                    Pair("우유", 4),
                    Pair("요플레", 20),
                    Pair("뚜러뻥", 1)
                )
            ),
            Arguments.of(
                "[주스-5],[우유-4],[요플레-20],[뚜러뻥-1],[불닭 볶음면-2]", listOf(
                    Pair("주스", 5),
                    Pair("우유", 4),
                    Pair("요플레", 20),
                    Pair("뚜러뻥", 1),
                    Pair("불닭 볶음면", 2)
                )
            )
        )
    }
}