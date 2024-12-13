package helpers

import java.text.NumberFormat
import java.util.Locale

fun formatDoubleToBRL(value:Double):String{
    return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(value)
}