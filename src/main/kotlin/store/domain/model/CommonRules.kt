package store.domain.model

enum class CommonRules(private val msg: String) {
    OUT_OF_STOCK("재고 없음"),
    STOCK_UNIT("개");

    override fun toString(): String = msg
}