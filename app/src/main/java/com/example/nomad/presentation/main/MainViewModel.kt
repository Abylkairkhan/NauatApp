package com.example.nomad.presentation.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomad.additional.Result
import com.example.nomad.data.repository.IMenuRepository
import com.example.nomad.domain.models.FoodTypeModel
import com.example.nomad.domain.models.MainMenuModel
import com.example.nomad.domain.models.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: IMenuRepository
) : ViewModel() {

    private var _mainMenuList = MutableLiveData<List<MainMenuModel>>()
    val mainMenuList: LiveData<List<MainMenuModel>>
        get() = _mainMenuList

    private var _foodTypeList = MutableLiveData<List<FoodTypeModel>>()
    val foodTypeList: LiveData<List<FoodTypeModel>>
        get() = _foodTypeList

    private var _productList = MutableLiveData<List<ProductModel>>()
    val productList: LiveData<List<ProductModel>>
        get() = _productList

    private var _position = MutableLiveData<Int>()
    val position: LiveData<Int>
        get() = _position

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun fetchProductByPattern(pattern: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchProductByPattern(pattern)
            if (result.isEmpty()) {
                Log.d("MyLog", "MainViewModel cant take data from Repository")
            } else {
                _productList.postValue(result!!)
            }
        }
    }

    fun fetchMainMenu(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = repository.fetchMainMenu(context)) {
                is Result.Success<*> -> {
                    _mainMenuList.postValue(result.data as List<MainMenuModel>)
                }
                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    fun fetchFoodType() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = repository.fetchFoodType()) {
                is Result.Success<*> -> {
                    _foodTypeList.postValue(result.data as List<FoodTypeModel>)
                }
                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    fun fetchProduct(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = repository.fetchProduct(context)) {
                is Result.Success<*> -> {
                    Log.d("MyLog", "product success")
                    _productList.postValue(result.data as List<ProductModel>)
                }
                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    fun fetchFoodTypeByID(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchFoodTypeByType(type)
            if (result.isEmpty()) {
                Log.d("MyLog", "Didn't find items by ID")
            } else {
                _foodTypeList.postValue(result)
            }
        }
    }

    fun scrollToPosition(documentID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val pos = if (repository.getFoodTypePosition(documentID) == 1) 0 else repository.getFoodTypePosition(documentID) - 2
            _position.postValue(pos)
        }
    }

    fun insert() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert()
        }
    }
}