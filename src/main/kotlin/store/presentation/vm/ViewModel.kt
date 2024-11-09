package store.presentation.vm

import store.domain.usecase.CheckOrderValidationUseCase
import store.domain.repository.ProductRepository
import store.domain.usecase.ExtractOrdersUseCase
import store.domain.usecase.GetInProgressPromotionUseCase
import store.presentation.vm.model.StoreState
import store.presentation.event.UiEvent
import store.presentation.vm.model.Order
import store.presentation.vm.model.Orders
import store.presentation.vm.model.PromotionState

class ViewModel(
    private val productRepository: ProductRepository,
    private val checkOrderValidationUseCase: CheckOrderValidationUseCase,
    private val extractOrdersUseCase: ExtractOrdersUseCase,
    private val getInProgressPromotionUseCase: GetInProgressPromotionUseCase
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
        val extractedOrders = extractOrdersUseCase(order)
        val newOrders = Orders(extractedOrders.map {
            Order(it.first, it.second, PromotionState.NoPromotion)
        }
        )

        _state = _state.copy(orders = newOrders)
        updateOrder()
    }

    private fun updateOrder() {
        val updateOrders = _state.orders.items.map { order ->
            val hasPromotion = _state.products.hasPromotion(order.name)
            setOrderPromotion(order, hasPromotion)
        }
        _state = _state.copy(orders = Orders(updateOrders))
    }

    private fun setOrderPromotion(currentOrder: Order, hasPromotion: String?): Order {
        return if (hasPromotion != null) {
            val isInProgressPromotion = getInProgressPromotionUseCase(hasPromotion)
            val promotionState = PromotionState.create(isInProgressPromotion)
            currentOrder.copy(promotion = promotionState)
        } else {
            currentOrder
        }
    }
}