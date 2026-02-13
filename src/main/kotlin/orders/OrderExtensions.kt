package orders

/**
 * Applies a percentage discount to every product in the order.
 *
 * Hint: use [products] to read the current list, [removeProductById] and
 * [addProduct] to replace each product with a discounted copy.
 * Use [Product.copy] to create a new product with a modified price.
 *
 * @param discountPercent discount percentage (e.g. 10 means 10%)
 * @param logger optional callback invoked with a log message for each product
 */
fun Order.applyDiscount(
    discountPercent: Int,
    logger: ((String) -> Unit)? = null
) {
    var productWithDiscount: Product

    this.products.forEach { product ->
        productWithDiscount =
            product.copy(price = (product.price * (1 - discountPercent.toDouble() / 100)).toInt())
        this.removeProductById(product.id)
        logger?.invoke("Removed product ${product}")
        this.addProduct(productWithDiscount)
        logger?.invoke("Added product ${productWithDiscount}")
    }
}
