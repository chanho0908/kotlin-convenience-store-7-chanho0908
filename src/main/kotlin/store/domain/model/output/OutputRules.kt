package store.domain.model.output

import store.domain.model.product.ProductItem

enum class OutputRules(private val msg: String) {
    WELCOME("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    GUIDE("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    OUT_OF_STOCK("재고 없음"),
    STOCK_UNIT("개"),
    PRODUCT("- %s %s원 %s %s"),
    NOT_RECEIVED_PROMOTION("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    STORAGE_STOCK("현재 %s %s개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    YES("Y"),
    NO("N");

    override fun toString(): String = msg

    companion object {
        fun productFormat(product: ProductItem): String {
            return PRODUCT.msg.format(
                product.name,
                product.price,
                product.quantity,
                product.promotion
            ).trim()
        }

        fun notReceivedPromotionFormat(name: String): String {
            return NOT_RECEIVED_PROMOTION.toString().format(name)
        }

        fun storageStockFormat(name: String, quantity: Int): String {
            return STORAGE_STOCK.toString().format(name, quantity)
        }
    }
}