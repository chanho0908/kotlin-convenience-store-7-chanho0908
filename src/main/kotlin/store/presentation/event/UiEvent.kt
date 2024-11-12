package store.presentation.event

import store.domain.model.receipt.Receipt

sealed class UiEvent {
    data class Loading(val message: String) : UiEvent()
    data class UserAccess(val message: String) : UiEvent()
    data class FinalizeOrder(
        val notReceivedPromotionMsg: List<String>?,
        val shortageStockMsg: List<String>?,
    ) : UiEvent()
    data class MakeOutReceipt(val receipt: Receipt) : UiEvent()
}