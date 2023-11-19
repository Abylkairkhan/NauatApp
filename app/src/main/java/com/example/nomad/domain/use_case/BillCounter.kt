package com.example.nomad.domain.use_case

import com.example.nomad.domain.models.ProductModel

object BillCounter {

    private var currentBill: Long = 0;
    private var allProducts = listOf<ProductModel>()

    fun getBucketProducts(): List<ProductModel> {
        return allProducts
    }

    fun setBucketProducts(data: List<ProductModel>){
        allProducts = data
    }

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