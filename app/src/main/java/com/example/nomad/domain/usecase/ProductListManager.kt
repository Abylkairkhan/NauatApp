package com.example.nomad.domain.usecase

import com.example.nomad.domain.models.ProductModel

object ProductListManager {

    private val cartItems: MutableList<ProductModel> = mutableListOf()

    fun getCartItems(): List<ProductModel> =
        cartItems.toList()

    fun addToCart(product: ProductModel) {
        val existingItem = cartItems.find { it.id == product.id }

        if (existingItem != null) {
            existingItem.countInBucket++
        } else {
            val productCopy = product.copy(countInBucket = 1)
            cartItems.add(productCopy)
        }
    }

    fun removeFromCart(product: ProductModel) {
        val existingItem = cartItems.find { it.id == product.id }

        if (existingItem != null) {
            if (existingItem.countInBucket > 0) {
                existingItem.countInBucket--
                if (existingItem.countInBucket == 0) {
                    cartItems.remove(existingItem)
                }
            }
        }
    }

    fun getBill(): Long {
        var totalBill = 0L
        for (item in cartItems) {
            totalBill += item.price * item.countInBucket
        }
        return totalBill
    }

    fun clearCart() {
        cartItems.clear()
    }

}