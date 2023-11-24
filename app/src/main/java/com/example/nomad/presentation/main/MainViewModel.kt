package com.example.nomad.presentation.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomad.additional.Result
import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.data.repository.IMenuRepository
import com.example.nomad.data.use_case.FoodTypeConverter
import com.example.nomad.data.use_case.MainMenuConverter
import com.example.nomad.data.use_case.ProductConverter
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

    fun fetchMainMenu(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.fetchMainMenu(context)) {
                is Result.Success<*> -> {
                    _mainMenuList.postValue(result.data as List<MainMenuModel>)
                    for (item in result.data){
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
            when (val result = repository.fetchFoodTypeByType(type)) {
                is Result.Success<*> -> {
                    _foodTypeList.postValue(result.data as List<FoodTypeModel>)
                    for (item in result.data){
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
//        Log.d("MyLog", pr)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.fetchProductByType(productID, context)) {
                is Result.Success<*> -> {
                    _productList.postValue(result.data as List<ProductModel>)
                    for (item in result.data){
                        insertProductItem(ProductConverter.modelToEntity(item))
                    }
                }

                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    fun fetchFoodType() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.fetchFoodType()) {
                is Result.Success<*> -> {
                    _foodTypeList.postValue(result.data as List<FoodTypeModel>)
                }

                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }

    private fun insertMainMenuItem(mainMenuEntity: MainMenuEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMainMenuDataBase(mainMenuEntity)
        }
    }

    private fun insertFoodTypeItem(foodTypeEntity: FoodTypeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFoodTypeDataBase(foodTypeEntity)
        }
    }

    private fun insertProductItem(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertProductDataBase(productEntity)
        }
    }



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

    fun fetchProduct(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.fetchProduct(context)) {
                is Result.Success<*> -> {
                    _productList.postValue(result.data as List<ProductModel>)
                }

                is Result.Failure<*> -> {
                    _error.postValue(result.error as String)
                }
            }
        }
    }


    fun scrollToPosition(documentID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val pos =
                if (repository.getFoodTypePosition(documentID) == 1) 0 else repository.getFoodTypePosition(
                    documentID
                ) - 2
            _position.postValue(pos)
        }
    }

    fun insert() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert()
        }
    }
}