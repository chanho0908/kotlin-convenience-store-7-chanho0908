package store.presentation.vm

import store.domain.usecase.CheckOrderValidationUseCase
import store.domain.repository.ProductRepository
import store.domain.usecase.ExtractOrdersUseCase
import store.domain.usecase.GetUnexpiredPromotionUseCase
import store.presentation.vm.model.StoreState
import store.presentation.event.UiEvent
import store.presentation.vm.model.Order
import store.presentation.vm.model.Orders
import store.presentation.vm.model.toUiModel

class ViewModel(
    private val productRepository: ProductRepository,
    private val checkOrderValidationUseCase: CheckOrderValidationUseCase,
    private val extractOrdersUseCase: ExtractOrdersUseCase,
    private val getUnexpiredPromotionUseCase: GetUnexpiredPromotionUseCase
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

    fun orderBuyProduct(order: String) {
        val products = _state.products
        checkOrderValidationUseCase(order = order, products = products)
        onCompleteCheckOrderValidation(order)
    }

    private fun onCompleteCheckOrderValidation(order: String) {
        val extractedOrders = Orders(extractOrdersUseCase(order))
        _state = _state.copy(orders = extractedOrders)
        getUnexpiredPromotionUseCase()
    }

    private fun getUnexpiredPromotionUseCase() {
        checkPromotion()
    }

    private fun checkPromotion(){
        val updateOrders = _state.orders.items.map { order ->
            val hasPromotion = _state.products.hasPromotion(order.name)
            setOrderPromotion(order, hasPromotion)
        }
        _state = _state.copy(orders = Orders(updateOrders))
    }

    private fun setOrderPromotion(currentOrder: Order, hasPromotion: String?): Order{
        return if (hasPromotion != null){
            val unExpiredPromotion = getUnexpiredPromotionUseCase(hasPromotion)
            currentOrder.copy(promotion = unExpiredPromotion?.toUiModel())
        }else {
            currentOrder
        }
    }
}