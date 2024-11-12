package store.domain.usecase

import store.domain.ext.toKoreanUnit
import store.domain.model.output.OutputRules.Companion.recipeTotalFormat
import store.domain.model.output.OutputRules.Companion.recipeMembershipDiscountFormat
import store.domain.model.output.OutputRules.Companion.recipeEventDiscountFormat
import store.domain.model.output.OutputRules.Companion.recipeTotalPriceFormat
import store.domain.model.output.OutputRules.Companion.recipeProductFormat
import store.domain.model.output.OutputRules.Companion.recipePromotionFormat
import store.domain.model.output.OutputRules.Companion.memberShipDiscountMax
import store.domain.model.receipt.GiftReceipt
import store.domain.model.receipt.PaymentReceipt
import store.domain.model.receipt.PaymentReceiptItem
import store.domain.model.receipt.Receipt

class MakeOutReceiptUseCase {
    operator fun invoke(payment: PaymentReceipt, gift: GiftReceipt, membership: Boolean): Receipt {
        val totalData = calculateTotalQuantityAndPrice(payment, gift)
        val (membershipForm, membershipDiscount) =
            calculateMembershipDiscount(membership, getNonPromotionPrice(payment, gift))
        val discount = calculateDiscountFormat(membershipForm, payment, gift)

        val finalPrice = totalData.totalPrice - membershipDiscount - discount.second

        return Receipt(
            formatReceiptItems(payment, gift),
            formatGiftReceipt(gift),
            recipeTotalFormat(totalData.totalQuantity, totalData.totalPrice.toKoreanUnit()),
            discount.first,
            recipeTotalPriceFormat(finalPrice.toKoreanUnit())
        )
    }

    private fun formatReceiptItems(payment: PaymentReceipt, gift: GiftReceipt): String {
        val productQuantities = payment.items.map { product ->
            val totalQuantity = getSumOfProductQuantity(product, gift)
            val price = (product.originPrice * totalQuantity).toKoreanUnit()
            recipeProductFormat(product.name, totalQuantity, price)
        }
        return productQuantities.joinToString("\n")
    }

    private fun formatGiftReceipt(gift: GiftReceipt): String {
        return gift.items.entries.joinToString("\n") { (name, quantity) ->
            recipePromotionFormat(name, quantity)
        }
    }

    private fun calculateDiscountFormat(
        membershipForm: String,
        paymentReceipt: PaymentReceipt,
        giftReceipt: GiftReceipt
    ): Pair<String, Int> {
        val discount = calculateGiftDiscount(paymentReceipt, giftReceipt)
        val eventDiscountFormat = recipeEventDiscountFormat(discount.toKoreanUnit())
        return Pair("$eventDiscountFormat\n$membershipForm", discount)
    }

    private fun calculateGiftDiscount(paymentReceipt: PaymentReceipt, giftReceipt: GiftReceipt): Int {
        return giftReceipt.items.entries.sumOf { (giftName, giftQuantity) ->
            paymentReceipt.items.find { it.name == giftName }?.originPrice?.times(giftQuantity) ?: 0
        }
    }

    private fun calculateTotalQuantityAndPrice(payment: PaymentReceipt, gift: GiftReceipt): TotalData {
        val totalQuantity = payment.items.sumOf { product ->
            getSumOfProductQuantity(product, gift)
        }
        val totalPrice = payment.items.sumOf { product ->
            product.originPrice * getSumOfProductQuantity(product, gift)
        }
        return TotalData(totalQuantity, totalPrice)
    }

    private fun calculateMembershipDiscount(membership: Boolean, totalPrice: Int): Pair<String, Int> {
        return if (membership) {
            applyMembershipDiscount(totalPrice)
        } else {
            Pair(recipeMembershipDiscountFormat("0"), 0)
        }
    }

    private fun applyMembershipDiscount(totalPrice: Int): Pair<String, Int> {
        val discount = (totalPrice * 0.3).toInt().coerceAtMost(memberShipDiscountMax())
        return recipeMembershipDiscountFormat(discount.toKoreanUnit()) to discount
    }

    private fun getSumOfProductQuantity(product: PaymentReceiptItem, giftReceipt: GiftReceipt): Int {
        return giftReceipt.items[product.name]?.plus(product.quantity) ?: product.quantity
    }

    private fun getNonPromotionPrice(paymentReceipt: PaymentReceipt, giftReceipt: GiftReceipt): Int {
        return paymentReceipt.items.filterNot { giftReceipt.items.contains(it.name) }
            .sumOf { it.originPrice * it.quantity }
    }

    private data class TotalData(val totalQuantity: Int, val totalPrice: Int)
}
