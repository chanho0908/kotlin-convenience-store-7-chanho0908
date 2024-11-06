package store.domain.model

enum class CommonRules(private val value: String) {
    OUT_OF_STOCK("재고가 부족합니다.");

    fun getValue(): String = value
}