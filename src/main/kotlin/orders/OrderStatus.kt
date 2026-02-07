package orders

sealed class OrderStatus {
    object Created : OrderStatus()
    object Paid : OrderStatus()
    data class Cancelled(val reason: String) : OrderStatus()
}
