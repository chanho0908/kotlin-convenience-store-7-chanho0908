package store.presentation.vm.model

import store.domain.model.output.OutputRules
import store.domain.model.product.Products
import store.domain.model.promotion.Promotions
import store.domain.model.receipt.GiftReceipt
import store.domain.model.receipt.PaymentReceipt
import store.presentation.event.UiEvent

data class StoreState(
    val products: Products,
    val orders: Orders,
    val paymentReceipt : PaymentReceipt,
    val giftReceipt : GiftReceipt,
    val membershipApply: Boolean,
    val uiEvent: UiEvent
) {
    companion object {
        fun create() = StoreState(
            Products(emptyList()),
            Orders(emptyList()),
            PaymentReceipt(emptyList(), emptyMap()),
            GiftReceipt(mutableMapOf(), emptyList()),
            false,
            UiEvent.Loading(OutputRules.WELCOME.toString())
        )
    }
}