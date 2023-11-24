package com.example.nomad.domain.use_case

import android.util.Log
import com.example.nomad.domain.models.ProductModel

object ProductListManager {

    private val cartItems: MutableList<ProductModel> = mutableListOf()

    fun getCartItems(): List<ProductModel> {
        return cartItems.toList()
    }

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
            if (existingItem.countInBucket > 1) {
                existingItem.countInBucket--
            } else {
                cartItems.remove(existingItem)
            }
        }
    }

    fun getBill(): Long {
        var totalBill = 0L
        Log.d("MyLog", "Cart Items: ")
        for (item in cartItems) {
            Log.d("MyLog", "${item.nameRus}: price(${item.price}) itemCount (${item.countInBucket})")
            totalBill += item.price * item.countInBucket
        }

        return totalBill
    }

    fun clearCart() {
        cartItems.clear()
    }

}