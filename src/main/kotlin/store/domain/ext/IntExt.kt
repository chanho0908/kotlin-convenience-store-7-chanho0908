package store.domain.ext

import java.text.NumberFormat
import java.util.Locale

fun Int.toKoreanUnit() = NumberFormat.getNumberInstance(Locale.KOREA).format(this)
