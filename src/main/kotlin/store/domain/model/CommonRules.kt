package store.domain.model

enum class CommonRules(private val msg: String) {
    OUT_OF_STOCK("재고 없음");

    override fun toString(): String = msg
}