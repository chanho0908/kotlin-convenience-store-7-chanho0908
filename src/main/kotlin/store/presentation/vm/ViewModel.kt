package store.presentation.vm

import store.domain.usecase.CheckOrderValidationUseCase
import store.domain.repository.ProductRepository
import store.domain.usecase.ExtractOrdersUseCase
import store.presentation.vm.model.StoreState
import store.presentation.event.UiEvent
import store.presentation.vm.model.Orders

class ViewModel(
    private val productRepository: ProductRepository,
    private val promotionRepository: PromotionRepository,
    private val checkOrderValidationUseCase: CheckOrderValidationUseCase,
    private val extractOrdersUseCase: ExtractOrdersUseCase
) {
    private var _state = StoreState.create()
    val state get() = _state

    fun getStoreState() {
        val products = productRepository.getProduct()
        val guideMsg = products.makeCurrentStockGuideMessage()
        _state = state.copy(
            products = products,
            uiEvent = UiEvent.UserAccess(guideMsg)
        )
    }

        val products = _state.products
        checkOrderValidationUseCase(order = order, products = products)
        onCompleteCheckOrderValidation(order)
    }

    private fun onCompleteCheckOrderValidation(order: String) {
        val extractedOrders = Orders(extractOrdersUseCase(order))
        _state = _state.copy(orders = extractedOrders)

    private fun checkPromotion(){
        val updateOrders = _state.orders.items.map { order ->
            order.copy(promotion = _state.products.hasPromotion(order.name))
        }
        _state = _state.copy(orders = Orders(updateOrders))
    }
}