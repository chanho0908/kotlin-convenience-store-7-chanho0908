package store.domain.ext

import store.domain.model.Constants

fun String.isNumeric(): Boolean {
    return this.all { it.isDigit() }
}

fun String.splitByComma(): List<String> = this.split(Constants.COMMA.toString())

fun String.splitByHyphen(): List<String> = this.split(Constants.HYPHEN.toString())