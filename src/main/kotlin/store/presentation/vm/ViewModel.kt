package store.presentation.vm

import store.domain.model.Purchase
import store.domain.repository.ProductRepository
import store.domain.repository.PromotionRepository
import store.presentation.vm.model.StoreState
import store.presentation.event.UiEvent
import store.presentation.vm.model.MessageGenerator

class ViewModel(
    private val productRepository: ProductRepository,
    private val promotionRepository: PromotionRepository,
    private val messageGenerator: MessageGenerator
) {
    private var _state = StoreState.create()
    val state get() = _state

    fun getStoreState() {
        val products = productRepository.getProduct()
        val promotions = promotionRepository.getPromotion()
        val guideMsg = messageGenerator.makeCurrentStockGuideMessage(products)
        _state = state.copy(
            products = products,
            promotions = promotions,
            uiEvent = UiEvent.UserAccess(guideMsg)
        )
    }

    fun requestBuyProduct(request: String){
        val products = _state.products
        val promotions = _state.promotions

        Purchase(input = request, products = products, promotions = promotions)
    }
}