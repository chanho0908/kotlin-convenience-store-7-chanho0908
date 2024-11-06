package store.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import store.data.datasource.ProductDataSource
import store.data.datasource.PromotionDataSource
import store.data.dto.ProductResponse
import store.data.dto.PromotionResponse
import java.util.stream.Stream

class PromotionDataSourceTest {
    private lateinit var dataSource: PromotionDataSource

    @BeforeEach
    fun setUp() {
        dataSource = PromotionDataSource()
    }

    @ParameterizedTest
    @MethodSource("promotion_파일_읽어오기_테스트_데이터")
    fun `promotion_파일_읽어오기_테스트`(value: List<PromotionResponse>) {
        val actual = dataSource.getPromotion()

        assertThat(actual).containsExactlyInAnyOrderElementsOf(value)
    }

    companion object {
        @JvmStatic
        fun `promotion_파일_읽어오기_테스트_데이터`() = Stream.of(
            listOf(
                PromotionResponse(
                    "탄산2+1",
                    2,
                    1,
                    "2024-01-01",
                    "2024-12-31"
                ),
                PromotionResponse(
                    "MD추천상품",
                    1,
                    1,
                    "2024-01-01",
                    "2024-12-31"
                ),
                PromotionResponse(
                    "반짝할인",
                    1,
                    1,
                    "2024-11-01",
                    "2024-11-30"
                )
            )
        )
    }
}