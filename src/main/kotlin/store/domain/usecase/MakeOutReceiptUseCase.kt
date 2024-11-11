package store.domain.usecase

import store.domain.ext.toKoreanUnit
import store.domain.model.output.OutputRules
import store.domain.model.receipt.GiftReceipt
import store.domain.model.receipt.PaymentReceipt
import store.domain.model.receipt.PaymentReceiptItem
import store.domain.model.receipt.Receipt

class MakeOutReceiptUseCase {
    operator fun invoke(payment: PaymentReceipt, gift: GiftReceipt, membership: Boolean): Receipt {
        val totalData = makeTotalQuantityAndPrice(payment, gift)
        val (membershipForm, membershipDiscount) = getMembershipDiscount(membership, totalData.totalPrice)
        return Receipt(
            makeReceiptForm(payment, gift),
            makeGiftReceiptForm(gift),
            OutputRules.recipeTotalFormat(totalData.totalQuantity, totalData.totalPrice.toKoreanUnit()),
            makeDiscountFormat(membershipForm, payment, gift),
            OutputRules.recipeTotalPriceFormat((totalData.totalPrice - membershipDiscount).toKoreanUnit())
        )
    }

    private fun makeReceiptForm(paymentReceipt: PaymentReceipt, giftReceipt: GiftReceipt): String {
        return paymentReceipt.items.joinToString("\n") { product ->
            val totalProductQuantity = getSumOfProductQuantity(product, giftReceipt)
            val price = (product.originPrice * totalProductQuantity).toKoreanUnit()
            OutputRules.recipeProductFormat(product.name, totalProductQuantity, price)
        }
    }

    private fun makeGiftReceiptForm(giftReceipt: GiftReceipt): String {
        return giftReceipt.items.entries.joinToString("\n") { (name, quantity) ->
            OutputRules.recipePromotionFormat(name, quantity)
        }
    }

    private fun makeDiscountFormat(
        membershipDiscountForm: String,
        paymentReceipt: PaymentReceipt,
        giftReceipt: GiftReceipt
    ): String {
        val discount = giftReceipt.items.mapNotNull { (giftName, giftQuantity) ->
            paymentReceipt.items.find { it.name == giftName }?.originPrice?.times(giftQuantity)
        }.sum()
        return "${OutputRules.recipeEventDiscountFormat(discount)}\n$membershipDiscountForm"
    }

    private fun makeTotalQuantityAndPrice(payment: PaymentReceipt, gift: GiftReceipt): TotalData {
        var totalQuantity = 0
        var totalPrice = 0

        payment.items.forEach { product ->
            val totalProductQuantity = getSumOfProductQuantity(product, gift)
            totalQuantity += totalProductQuantity
            totalPrice += product.originPrice * totalProductQuantity
        }
        return TotalData(totalQuantity, totalPrice)
    }

    private fun getMembershipDiscount(membership: Boolean, totalPrice: Int): Pair<String, Int> {
        return if (membership) applyMembershipDiscount(totalPrice) else "0" to 0
    }

    private fun applyMembershipDiscount(totalPrice: Int): Pair<String, Int> {
        val membershipDiscount =
            (totalPrice * 0.3).toInt().coerceAtMost(OutputRules.memberShipDiscountMax())
        val membershipFormat =
            OutputRules.recipeMembershipDiscountFormat(membershipDiscount.toKoreanUnit())
        return membershipFormat to membershipDiscount
    }

    private fun getSumOfProductQuantity(
        product: PaymentReceiptItem,
        giftReceipt: GiftReceipt
    ): Int {
        return giftReceipt.items[product.name]?.plus(product.quantity) ?: product.quantity
    }

    private data class TotalData(val totalQuantity: Int, val totalPrice: Int)
}