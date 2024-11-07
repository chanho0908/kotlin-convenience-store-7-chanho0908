package store.presentation.view

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
            is UiEvent.Loading -> {
                outputView.printMessage(event.message)
                onCompleteShowWelcomeMessage()
            }
            is UiEvent.UserAccess -> {
                outputView.printMessage(event.message)
                getBuyProductInfo()
            }
        }
    }

    private fun getBuyProductInfo() = retryWhenNoException {
        val input = inputView.readItem()
        viewModel.requestBuyProduct(input)
    }

    private fun onCompleteShowWelcomeMessage() {
        viewModel.getStoreState()
        checkUiState()
    }
}