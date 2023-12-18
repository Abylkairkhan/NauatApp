package com.example.nomad.domain.usecase

import com.example.nomad.additional.Result
import com.example.nomad.data.repository.IMenuRepository

class GetFoodTypeByType(
    private val repository: IMenuRepository
) {
    suspend fun execute(type: String): Result = repository.fetchFoodTypeByType(type)
}