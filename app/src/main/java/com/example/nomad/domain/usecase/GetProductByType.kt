package com.example.nomad.domain.usecase

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.data.repository.IMenuRepository

class GetProductByType(
    private val repository: IMenuRepository
) {
    suspend fun execute(type: String, context: Context): Result = repository.fetchProductByType(type, context)
}