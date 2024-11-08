package store.presentation.vm

import store.domain.model.product.Products
import store.domain.usecase.CheckOrderValidationUseCase
import store.domain.repository.ProductRepository
import store.domain.repository.PromotionRepository
import store.presentation.vm.model.StoreState
import store.presentation.event.UiEvent

class ViewModel(
    private val productRepository: ProductRepository,
    private val promotionRepository: PromotionRepository,
    private val messageGenerator: MessageGenerator,
    private val checkOrderValidationUseCase: CheckOrderValidationUseCase
) {
    private var _state = StoreState.create()
    val state get() = _state

    fun getStoreState() {
        val products = productRepository.getProduct()
        val promotions = promotionRepository.getPromotion()
        val guideMsg = products.makeCurrentStockGuideMessage()
        _state = state.copy(
            products = products,
            promotions = promotions,
            uiEvent = UiEvent.UserAccess(guideMsg)
        )
    }

    fun requestBuyProduct(request: String){
        val products = _state.products
        checkOrderValidationUseCase(order = request, products = products)
    }
}