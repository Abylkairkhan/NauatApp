package com.example.nomad.domain.use_case

import com.example.nomad.domain.models.ProductModel

object BillCounter {

    private var currentBill: Long = 0;

    fun getBill(): Long {
        return currentBill
    }

    fun setBill(price: Long, addToBucket: Boolean) {
        if (addToBucket) {
            currentBill += price
        }
        else if(currentBill > 0) {
            currentBill -= price
        }
    }
}