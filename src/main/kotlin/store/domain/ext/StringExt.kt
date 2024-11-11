package store.domain.ext

import store.domain.model.Constants
import store.domain.model.Constants.SQUARE_BRACKETS_LEFT
import store.domain.model.Constants.SQUARE_BRACKETS_RIGHT
import store.domain.model.output.OutputRules
import store.domain.model.output.OutputRules.STOCK_UNIT
import store.domain.model.output.OutputRules.NO
import store.domain.model.output.OutputRules.YES
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toLocalDate(): LocalDate {
    val pattern = "yyyy-MM-dd"
    val format = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(this, format)
}

fun String.splitByComma(): List<String> = this.split(Constants.COMMA.toString())

fun String.splitByHyphen(): List<String> = this.split(Constants.HYPHEN.toString())

fun String.extractProductName(): String {
    val productName = this.splitByHyphen().first()
    return productName.trim().removePrefix("$SQUARE_BRACKETS_LEFT")
}

fun String.extractProductQuantity(): String {
    val productQuantity = this.splitByHyphen().last()
    return productQuantity.trim().removeSuffix("$SQUARE_BRACKETS_RIGHT")
}

fun String.removeStockUnitSuffix(): Int {
    if (this == "${OutputRules.OUT_OF_STOCK}") {
        return 0
    }
    return this.removeSuffix("$STOCK_UNIT").toInt()
}

fun String.isNo(): Boolean = this.equals(NO.toString(), ignoreCase = true)

fun String.isYes(): Boolean = this.equals(YES.toString(), ignoreCase = true)