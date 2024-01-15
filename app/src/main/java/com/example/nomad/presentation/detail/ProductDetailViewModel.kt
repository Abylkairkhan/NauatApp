package com.example.nomad.presentation.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomad.additional.Result
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.usecase.GetProductByID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductByID: GetProductByID
) : ViewModel() {

    private var _product = MutableLiveData<ProductModel>()
    val product: LiveData<ProductModel>
        get() = _product

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun fetchProductByID(id: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = getProductByID.execute(id.toLong(), context)) {
                is Result.Success<*> -> {
                    _product.postValue(response.data as ProductModel)
                }
                is Result.Failure<*> -> {
                    _error.postValue(response.error as String)
                }
            }
        }
    }

}