package com.example.nomad.domain.usecase

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.data.repository.IMenuRepository

class GetProductByID(
    private val repository: IMenuRepository
) {
    suspend fun execute(id: Long, context: Context): Result = repository.fetchProductByID(id, context)
}