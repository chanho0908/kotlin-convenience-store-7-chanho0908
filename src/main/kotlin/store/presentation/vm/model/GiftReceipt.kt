package store.presentation.vm.model

import store.domain.model.output.OutputRules

data class GiftReceipt(
    val items: MutableMap<String, Int>,
    val notReceivedPromotion: List<String>
) {
    fun createNotReceivedPromotionMsg(): List<String>? {
        if (notReceivedPromotion.isNotEmpty()) {
            val notReceivedPromotionMsg = mutableListOf<String>()
            notReceivedPromotion.forEach {
                notReceivedPromotionMsg.add(OutputRules.notReceivedPromotionFormat(it))
            }
            return notReceivedPromotionMsg
        }
        return null
    }

    fun removeNotReceivedPromotion(idx: Int): List<String> {
        return this.notReceivedPromotion.filterNot { it == notReceivedPromotion[idx] }
    }

    fun addNotReceivedPromotion(idx: Int){
        val notReceivedPromotion = this.notReceivedPromotion[idx]
        items[notReceivedPromotion] = items.getOrDefault(notReceivedPromotion, 0) + 1
    }
}