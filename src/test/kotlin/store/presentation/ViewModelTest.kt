package store.presentation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import store.data.repository.FakeProductRepositoryImpl
import store.data.repository.FakePromotionRepositoryImpl
import store.domain.model.product.ProductItem
import store.domain.model.promotion.PromotionItem
import store.domain.model.product.Products
import store.domain.model.promotion.Promotions
import store.presentation.event.UiEvent
import store.presentation.vm.ViewModel
import store.presentation.vm.model.MessageGenerator
import store.presentation.vm.model.StoreState

class ViewModelTest {
    private lateinit var productRepository: FakeProductRepositoryImpl
    private lateinit var promotionRepository: FakePromotionRepositoryImpl
    private lateinit var messageGenerator: MessageGenerator
    private lateinit var viewModel: ViewModel

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepositoryImpl()
        promotionRepository = FakePromotionRepositoryImpl()
        messageGenerator = MessageGenerator()
        viewModel = ViewModel(productRepository, promotionRepository, messageGenerator)
    }

    @Test
    fun `파일_호출_후_상태_변경_테스트`() {
        viewModel.getStoreState()
        val products = Products(
            listOf(
                ProductItem(name = "콜라", price = "1,000", quantity = "10개", promotion = "탄산2+1"),
                ProductItem(name = "콜라", price = "1,000", quantity = "10개", promotion = ""),
            )
        )

        val promotions = Promotions(
            listOf(
                PromotionItem("탄산2+1", 2, 1, "2024-01-01", "2024-12-31"),
                PromotionItem("MD추천상품",1,1,"2024-01-01","2024-12-31"),
                PromotionItem("반짝할인",1,1,"2024-11-01","2024-11-30")
            )
        )
        val msg = MessageGenerator().makeCurrentStockGuideMessage(products)
        val actual = StoreState(products, promotions, UiEvent.UserAccess(msg))

        assertEquals(viewModel.state, actual)
    }
}

