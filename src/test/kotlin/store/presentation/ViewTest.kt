package store.presentation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import store.app.DependencyInjector
import store.domain.model.product.ProductItem
import store.domain.model.product.Products
import store.presentation.event.UiEvent
import store.presentation.view.MainView
import store.presentation.vm.model.MessageGenerator
import java.util.stream.Stream

class ViewTest {
    private lateinit var di: DependencyInjector
    private lateinit var messageGenerator: MessageGenerator
    private lateinit var view: MainView

    @BeforeEach
    fun setUp() {
        di = DependencyInjector()
        view = di.injectMainView()
        messageGenerator = MessageGenerator()
    }

    @ParameterizedTest
    @MethodSource("UI_상태에_따른_상태_메시지_출력_테스트_데이터")
    fun `UI_상태에_따른 상태메시지_출력_테스트`(value: Products) {

        val msg = messageGenerator.makeCurrentStockGuideMessage(value)

        val expected = UiEvent.Loading(msg).message

        assertEquals(
            expected,
            "- 콜라 1,000원 10개 탄산2+1\n" +
                    "- 콜라 1,000원 10개\n" +
                    "- 사이다 1,000원 8개 탄산2+1\n" +
                    "- 사이다 1,000원 7개\n" +
                    "- 오렌지주스 1,800원 9개 MD추천상품\n" +
                    "- 오렌지주스 1,800원 재고 없음\n" +
                    "- 탄산수 1,200원 5개 탄산2+1\n" +
                    "- 탄산수 1,200원 재고 없음\n" +
                    "- 물 500원 10개\n" +
                    "- 비타민워터 1,500원 6개\n" +
                    "- 감자칩 1,500원 5개 반짝할인\n" +
                    "- 감자칩 1,500원 5개\n" +
                    "- 초코바 1,200원 5개 MD추천상품\n" +
                    "- 초코바 1,200원 5개\n" +
                    "- 에너지바 2,000원 5개\n" +
                    "- 정식도시락 6,400원 8개\n" +
                    "- 컵라면 1,700원 1개 MD추천상품\n" +
                    "- 컵라면 1,700원 10개\n\n" +
                    "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"
        )
    }


    companion object {
        @JvmStatic
        fun `UI_상태에_따른_상태_메시지_출력_테스트_데이터`() = Stream.of(
            Products(
                listOf(
                    ProductItem(name = "콜라", price = "1,000", quantity = "10개", promotion = "탄산2+1"),
                    ProductItem(name = "콜라", price = "1,000", quantity = "10개", promotion = ""),
                    ProductItem(name = "사이다", price = "1,000", quantity = "8개", promotion = "탄산2+1"),
                    ProductItem(name = "사이다", price = "1,000", quantity = "7개", promotion = ""),
                    ProductItem(name = "오렌지주스", price = "1,800", quantity = "9개", promotion = "MD추천상품"),
                    ProductItem(name = "오렌지주스", price = "1,800", quantity = "재고 없음", promotion = ""),
                    ProductItem(name = "탄산수", price = "1,200", quantity = "5개", promotion = "탄산2+1"),
                    ProductItem(name = "탄산수", price = "1,200", quantity = "재고 없음", promotion = ""),
                    ProductItem(name = "물", price = "500", quantity = "10개", promotion = ""),
                    ProductItem(name = "비타민워터", price = "1,500", quantity = "6개", promotion = ""),
                    ProductItem(name = "감자칩", price = "1,500", quantity = "5개", promotion = "반짝할인"),
                    ProductItem(name = "감자칩", price = "1,500", quantity = "5개", promotion = ""),
                    ProductItem(name = "초코바", price = "1,200", quantity = "5개", promotion = "MD추천상품"),
                    ProductItem(name = "초코바", price = "1,200", quantity = "5개", promotion = ""),
                    ProductItem(name = "에너지바", price = "2,000", quantity = "5개", promotion = ""),
                    ProductItem(name = "정식도시락", price = "6,400", quantity = "8개", promotion = ""),
                    ProductItem(name = "컵라면", price = "1,700", quantity = "1개", promotion = "MD추천상품"),
                    ProductItem(name = "컵라면", price = "1,700", quantity = "10개", promotion = "")
                )
            )
        )
    }

}