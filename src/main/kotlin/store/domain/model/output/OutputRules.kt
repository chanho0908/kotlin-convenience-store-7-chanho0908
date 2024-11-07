package store.domain.model.output

import store.domain.model.product.Item

enum class OutputRules(private val msg: String) {
    WELCOME("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    GUIDE("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    OUT_OF_STOCK("재고 없음"),
    STOCK_UNIT("개"),
    PRODUCT("- %s %s원 %s %s");

    override fun toString(): String = msg

    companion object {
        fun productFormat(product: Item): String {
            return PRODUCT.msg.format(
                product.name,
                product.price,
                product.quantity,
                product.promotion
            )
        }
    }
}