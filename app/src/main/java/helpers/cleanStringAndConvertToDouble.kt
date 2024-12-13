package helpers

fun cleanStringAndConvertToDouble(string: String): Double {
    val cleanString = string.replace("""[R$,.]""".toRegex(), "").trim()
    return cleanString.toDouble() / 100
}