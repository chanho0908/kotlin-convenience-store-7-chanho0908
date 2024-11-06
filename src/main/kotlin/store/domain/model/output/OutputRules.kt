package store.domain.model.output

enum class OutputRules(private val msg: String) {
    WELCOME("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    GUIDE("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    PRODUCT("- %s %s원 %s %s");

    override fun toString(): String = msg

    companion object {
        fun productFormat(
            name: String,
            price: String,
            quantity: String,
            promotion: String?
        ): String {
            return PRODUCT.msg.format(name, price, quantity, promotion)
        }
    }
}