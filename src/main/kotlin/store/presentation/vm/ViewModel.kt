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

    fun initializeStoreState() {
        val products = productRepository.getProduct()
        val guideMsg = products.makeCurrentStockGuideMessage()
        _state = state.copy(
            products = products,
            uiEvent = UiEvent.UserAccess(guideMsg)
        )
    }

    fun processOrder(order: String) {
        val products = _state.products
        checkOrderValidationUseCase(order = order, products = products)
        onCompleteCheckOrderValidation(order)
    }

    private fun onCompleteCheckOrderValidation(order: String) {
        val extractedOrders = extractOrdersUseCase(order)
        val newOrders = Orders(extractedOrders.map { Order(it.first, it.second, NoPromotion) })

        _state = _state.copy(orders = newOrders)
        applyPromotionsToOrders()
    }

    private fun applyPromotionsToOrders() {
        val updateOrders = _state.orders.items.map { order ->
            val hasPromotion = _state.products.hasPromotion(order.name)
            assignPromotionToOrder(order, hasPromotion)
        }
        _state = _state.copy(orders = Orders(updateOrders))
        finalizeOrderCalculation()
    }

    private fun assignPromotionToOrder(currentOrder: Order, promotionName: String?): Order {
        return promotionName?.let {
            val promotionState = PromotionState.create(getInProgressPromotionUseCase(it))
            currentOrder.copy(promotion = promotionState)
        } ?: currentOrder
    }

    private fun finalizeOrderCalculation() {
        _state.orders.items.forEach { order ->
            val promotion = order.promotion
            handleOrderPromotionStatus(promotion, order)
        }
    }

    private fun handleOrderPromotionStatus(promotion: PromotionState, order: Order) {
        val productPrice = getProductPrice(order.name)

        when (promotion) {
            is InProgress -> handleOrderPromotionStock(order, productPrice, promotion)
            is NotInProgress, NoPromotion ->
                addPaymentReceipt(order.name, order.quantity, productPrice)
        }
    }
        } else {
            currentOrder
        }
    }
}