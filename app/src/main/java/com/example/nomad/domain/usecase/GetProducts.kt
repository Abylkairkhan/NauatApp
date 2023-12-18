package com.example.nomad.domain.usecase

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.data.repository.IMenuRepository

class GetProducts(
    private val repository: IMenuRepository
) {
    suspend fun execute(context: Context): Result = repository.fetchProduct(context)
}