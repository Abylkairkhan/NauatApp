package com.example.nomad.presentation.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomad.additional.Result
import com.example.nomad.additional.TopScrollView
import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.data.use_case.FoodTypeConverter
import com.example.nomad.data.use_case.MainMenuConverter
import com.example.nomad.data.use_case.ProductConverter
import com.example.nomad.domain.models.FoodTypeModel
import com.example.nomad.domain.models.MainMenuModel
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.usecase.GetFoodTypeByType
import com.example.nomad.domain.usecase.GetMainMenuData
import com.example.nomad.domain.usecase.GetProductByPattern
import com.example.nomad.domain.usecase.GetProductByType
import com.example.nomad.domain.usecase.GetProducts
import com.example.nomad.domain.usecase.GetServePercentage
import com.example.nomad.domain.usecase.InsertLocalData
import com.example.nomad.domain.usecase.ProductListManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getMainMenuData: GetMainMenuData,
    private val getFoodTypeByType: GetFoodTypeByType,
    private val getProductByType: GetProductByType,
    private val getProducts: GetProducts,
    private val getProductByPattern: GetProductByPattern,
    private val insertLocalData: InsertLocalData,
    private val getServePercentage: GetServePercentage
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

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun fetchMainMenu(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getMainMenuData.execute(context)) {
                is Result.Success<*> -> {
                    _mainMenuList.postValue(result.data as List<MainMenuModel>)
                    for (item in result.data) {
                        insertMainMenuItem(MainMenuConverter.modelToEntity(item))
                    }
                }

                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    fun fetchFoodTypeByID(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getFoodTypeByType.execute(type)) {
                is Result.Success<*> -> {
                    _foodTypeList.postValue(result.data as List<FoodTypeModel>)
                    for (item in result.data) {
                        insertFoodTypeItem(FoodTypeConverter.modelToEntity(item))
                    }
                }

                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    fun fetchProductByID(productID: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getProductByType.execute(productID, context)) {
                is Result.Success<*> -> {
                    _productList.postValue(result.data as List<ProductModel>)
                    for (item in result.data) {
                        insertProductItem(ProductConverter.modelToEntity(item))
                    }
                }

                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    private fun insertMainMenuItem(mainMenuEntity: MainMenuEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertLocalData.insertMainMenuItem(mainMenuEntity)
        }
    }

    private fun insertFoodTypeItem(foodTypeEntity: FoodTypeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertLocalData.insertFoodTypeItem(foodTypeEntity)
        }
    }

    private fun insertProductItem(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertLocalData.insertProductItem(productEntity)
        }
    }


    fun fetchProductByPattern(pattern: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getProductByPattern.execute(pattern)
            if (result.isEmpty()) {
                _error.postValue("Didn't find any product")
            } else {
                _productList.postValue(result)
            }
        }
    }

    fun fetchProduct(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getProducts.execute(context)) {
                is Result.Success<*> -> {
                    _productList.postValue(result.data as List<ProductModel>)
                }

                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    fun fetchServePercentage() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getServePercentage.execute()) {
                is Result.Success<*> -> {
                    ProductListManager.setServePercentage(result.data as Long)
                }
                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }
}