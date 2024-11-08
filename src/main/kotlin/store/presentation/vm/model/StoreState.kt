package store.presentation.vm.model

import store.domain.model.output.OutputRules
import store.domain.model.product.Products
import store.domain.model.promotion.Promotions
import store.presentation.event.UiEvent

data class StoreState(
    val products: Products,
    val promotions: Promotions,
    val orders: Orders,
    val uiEvent: UiEvent
) {
    companion object {
        fun create() = StoreState(
            Products(emptyList()),
            Promotions(emptyList()),
            Orders(emptyList()),
            UiEvent.Loading(OutputRules.WELCOME.toString())
        )
    }
}