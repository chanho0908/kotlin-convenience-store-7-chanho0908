package store.presentation.view

import store.domain.ext.isNo
import store.domain.ext.isYes
import store.domain.model.output.OutputRules
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
        when (val event = viewModel.state.uiEvent) {
            is UiEvent.Loading -> onUiEventLoading(event)
            is UiEvent.UserAccess -> onUiEventUserAccess(event)
            is UiEvent.FinalizeOrder -> onUiEventFinalizeOrder(event)
            is UiEvent.MakeOutReceipt -> onUiEventFinalizeOrder(event)
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
        askMemberShip()
        makeOutRecipe()
    }

    private fun additionalPromotion(notReceivedPromotionMessages: List<String>?) {
        notReceivedPromotionMessages?.let { message ->
            message.forEachIndexed { idx, value ->
                val response = suggestAdditionalOption(value)
                viewModel.addOrRemoveNotReceivedPromotion(idx, response)
            }
        }
    }

    private fun additionalShortageStock(shortageStockMessages: List<String>?) {
        shortageStockMessages?.let { messages ->
            messages.forEach { product ->
                val response = suggestAdditionalOption(product)
                if (response.isNo()) viewModel.noPayShortageStock(product)
            }
        }
    }

    private fun askMemberShip() {
        val input = suggestAdditionalOption(OutputRules.MEMBERSHIP_DISCOUNT.toString())
        viewModel.whenUserRequestMembership(input)
    }

    private fun askForAdditionalPurchase(){
        outputView.printAskForAdditionalPurchase()
        val input =  retryWhenNoException {
            val input = inputView.readItem()
            viewModel.whenUserInputYesOrNo(input)
            input
        }
        if (input.isYes()) {
            viewModel.whenUserSelectAdditionalPurchase()
            checkUiState()
        }
    }

    private fun suggestAdditionalOption(msg: String): String {
        outputView.printMessage(msg)
        return retryWhenNoException {
            val input = inputView.readItem()
            viewModel.whenUserInputYesOrNo(input)
            input
        }
    }

    private fun makeOutRecipe(){
        viewModel.makeOutRecipeState()
        checkUiState()
    }

    private fun onUiEventFinalizeOrder(event: UiEvent.MakeOutReceipt){
        outputView.printReceiptHeader()
        outputView.printMessage(event.receipt.productReceipt)
        outputView.printPromotionReceiptHeader()
        outputView.printMessage(event.receipt.promotionRecipe)
        outputView.printDottedLine()
        outputView.printMessage(event.receipt.totalAmount)
        outputView.printMessage(event.receipt.eventDiscount)
        outputView.printMessage(event.receipt.payment)
        askForAdditionalPurchase()
    }
}