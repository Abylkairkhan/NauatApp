package com.example.nomad.domain.use_case

import com.example.nomad.domain.models.ProductModel

object ProductListManager {

    private var allProducts = listOf<ProductModel>()

    fun addCountToProduct(position: Int, add: Boolean) {
        if (add) allProducts[position].countInBucket +=1
        else allProducts[position].countInBucket -=1
    }

    fun getProducts(): List<ProductModel> {
        return allProducts
    }

    fun setProducts(data: List<ProductModel>) {
        allProducts = data
    }

}