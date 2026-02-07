package orders

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OrderTest {

    @Test
    fun `addProduct ignores null`() {
        val order = Order(1)
        order.addProduct(null)

        assertEquals(0, order.calculateTotal())
    }

    @Test
    fun `calculate total sum`() {
        val order = Order(1)
        order.addProduct(Product(1, "A", 100))
        order.addProduct(Product(2, "B", 200))

        assertEquals(300, order.calculateTotal())
    }

    @Test
    fun `remove product by id`() {
        val order = Order(1)
        order.addProduct(Product(1, "A", 100))
        order.addProduct(Product(2, "B", 200))

        order.removeProductById(1)

        assertEquals(200, order.calculateTotal())
    }

    @Test
    fun `pay empty order throws exception`() {
        val order = Order(1)

        assertThrows(IllegalStateException::class.java) {
            order.pay()
        }
    }

    @Test
    fun `pay changes status`() {
        val order = Order(1)
        order.addProduct(Product(1, "A", 100))

        order.pay()

        assertTrue(order.status is OrderStatus.Paid)
    }

    @Test
    fun `cancel with null reason`() {
        val order = Order(1)
        order.cancel(null)

        val status = order.status as OrderStatus.Cancelled
        assertEquals("Unknown reason", status.reason)
    }

    @Test
    fun `process order with when`() {
        val order = Order(1)
        order.addProduct(Product(1, "A", 100))
        order.pay()

        val result = processOrder(order)

        assertEquals("Order 1 is paid", result)
    }

    @Test
    fun `apply discount reduces price`() {
        val order = Order(1)
        order.addProduct(Product(1, "A", 100))

        order.applyDiscount(10)

        assertEquals(90, order.calculateTotal())
    }

    @Test
    fun `apply discount with logger`() {
        val logs = mutableListOf<String>()
        val order = Order(1)
        order.addProduct(Product(1, "A", 100))

        order.applyDiscount(10) {
            logs.add(it)
        }

        assertTrue(logs.isNotEmpty())
    }
}
