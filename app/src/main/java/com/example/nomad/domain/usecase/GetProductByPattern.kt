package com.example.nomad.domain.usecase

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.data.repository.IMenuRepository
import com.example.nomad.domain.models.ProductModel

class GetProductByPattern(
    private val repository: IMenuRepository
) {
    suspend fun execute(pattern: String): MutableList<ProductModel> = repository.fetchProductByPattern(pattern)
}