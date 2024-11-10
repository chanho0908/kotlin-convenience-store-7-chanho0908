package store.presentation.vm.model

import store.domain.ext.removeNumberFormat
import store.domain.ext.toKoreanUnit
import store.domain.model.output.OutputRules

data class PaymentReceipt(
    val items: List<PaymentReceiptItem>,
    val shortageStock: Map<String, Int>
) {
    fun createShortageStockMsg(): List<String>? {
        if (shortageStock.isNotEmpty()) {
            val shortageStockMsg = mutableListOf<String>()
            shortageStock.forEach { (name, quantity) ->
                shortageStockMsg.add(OutputRules.storageStockFormat(name, quantity))
            }
            return shortageStockMsg
        }
        return null
    }

    fun removeFromShortageStock(productName: String): PaymentReceipt {
        val shortageStockAmount = this.shortageStock[productName] ?: 0
        val updateItems = this.items.map { item ->
            if (item.name == productName) createNewItem(item, shortageStockAmount)
            else item
        }
        return PaymentReceipt(updateItems, emptyMap())
    }

    private fun createNewItem(item: PaymentReceiptItem, shortageStockAmount: Int): PaymentReceiptItem{
        val shortageStockPrice = item.quantity * shortageStockAmount
        val newQuantity = item.quantity - shortageStockAmount
        val newPrice = item.price.removeNumberFormat() - shortageStockPrice
        return PaymentReceiptItem(item.name, newPrice.toKoreanUnit(), newQuantity)
    }
}

data class PaymentReceiptItem(
    val name: String,
    val price: String,
    val quantity: Int
)
