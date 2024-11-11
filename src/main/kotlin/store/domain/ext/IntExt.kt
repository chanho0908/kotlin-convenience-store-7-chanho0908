package store.domain.ext

import java.text.NumberFormat
import java.util.Locale

fun Int.toKoreanUnit(): String = NumberFormat.getNumberInstance(Locale.KOREA).format(this)
