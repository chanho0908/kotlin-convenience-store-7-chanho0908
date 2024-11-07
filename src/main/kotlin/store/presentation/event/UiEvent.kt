package store.presentation.event

sealed class UiEvent {
    data class Loading(val message: String) : UiEvent()
    data class UserAccess(val message: String): UiEvent()
}