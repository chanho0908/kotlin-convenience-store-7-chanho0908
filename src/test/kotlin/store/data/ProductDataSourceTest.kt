package store.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import store.data.datasource.ProductDataSource
import store.data.dto.ProductResponse
import java.util.stream.Stream

class ProductDataSourceTest {
    private lateinit var dataSource: ProductDataSource

    @BeforeEach
    fun setUp() {
        dataSource = ProductDataSource()
    }

    @ParameterizedTest
    @MethodSource("products_파일_읽어오가_테스트_데이터")
    fun `produts 파일 읽어오기 테스트`(value: List<ProductResponse>) {
        val actualProductList = dataSource.getProduct()

        assertThat(actualProductList).containsExactlyInAnyOrderElementsOf(value)
    }

    companion object {
        @JvmStatic
        fun `products_파일_읽어오가_테스트_데이터`() = Stream.of(
            listOf(
                ProductResponse("콜라", 1000, 10, "탄산2+1"),
                ProductResponse("콜라", 1000, 10, null),
                ProductResponse("사이다", 1000, 8, "탄산2+1"),
                ProductResponse("사이다", 1000, 7, null),
                ProductResponse("오렌지주스", 1800, 9, "MD추천상품"),
                ProductResponse("탄산수", 1200, 5, "탄산2+1"),
                ProductResponse("물", 500, 10, null),
                ProductResponse("비타민워터", 1500, 6, null),
                ProductResponse("감자칩", 1500, 5, "반짝할인"),
                ProductResponse("감자칩", 1500, 5, null),
                ProductResponse("초코바", 1200, 5, "MD추천상품"),
                ProductResponse("초코바", 1200, 5, null),
                ProductResponse("에너지바", 2000, 5, null),
                ProductResponse("정식도시락", 6400, 8, null),
                ProductResponse("컵라면", 1700, 1, "MD추천상품"),
                ProductResponse("컵라면", 1700, 10, null)
            )
        )
    }
}