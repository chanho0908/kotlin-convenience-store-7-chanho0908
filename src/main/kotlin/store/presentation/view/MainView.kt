package store.presentation.view

import store.domain.ext.isNo
import store.domain.ext.isYes
import store.presentation.vm.ViewModel
import store.presentation.event.UiEvent
import store.presentation.util.retryWhenNoException

class MainView(
    private val viewModel: ViewModel,
    private val inputView: InputView,
    private val outputView: OutputView
) {
    init {
        checkUiState()
    }

    private fun checkUiState() {
        when(val event = viewModel.state.uiEvent){
            is UiEvent.Loading -> onUiEventLoading(event)
            is UiEvent.UserAccess -> onUiEventUserAccess(event)
            is UiEvent.FinalizeOrder -> onUiEventFinalizeOrder(event)
        }
    }

    private fun onUiEventLoading(event: UiEvent.Loading) {
        outputView.printMessage(event.message)
        onCompleteShowWelcomeMessage()
    }

    private fun onCompleteShowWelcomeMessage() {
        viewModel.initializeStoreState()
        checkUiState()
    }

    private fun onUiEventUserAccess(event: UiEvent.UserAccess) {
        outputView.printMessage(event.message)
        getBuyProductInfo()
    }

    private fun getBuyProductInfo() = retryWhenNoException {
        val input = inputView.readItem()
        viewModel.processOrder(input)
        checkUiState()
    }

    private fun onUiEventFinalizeOrder(event: UiEvent.FinalizeOrder) {
        additionalPromotion(event.notReceivedPromotionMsg)
        additionalShortageStock(event.shortageStockMsg)
    }

    private fun additionalPromotion(notReceivedPromotionMessages: List<String>?){
        notReceivedPromotionMessages?.let { message ->
            message.forEachIndexed { idx, value ->
                val response = suggestAdditionalOption(value)
                viewModel.addOrRemoveNotReceivedPromotion(idx, response)
            }
        }
    }

    private fun additionalShortageStock(shortageStockMessages: List<String>?){
        shortageStockMessages?.let { messages ->
            messages.forEach { product ->
                val response = suggestAdditionalOption(product)
                if (response.isNo()) viewModel.noPayShortageStock(product)
            }
        }
    }

    private fun suggestAdditionalOption(msg: String): String{
        outputView.printShortageStockMsg(msg)
        return retryWhenNoException {
            val input = inputView.readItem()
            viewModel.whenUserInputYesOrNo(input)
            input
        }
    }
}