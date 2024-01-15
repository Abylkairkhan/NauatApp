package com.example.nomad.data.repository

import android.content.Context
import com.example.nomad.additional.Result
import com.example.nomad.data.local.NomadDataBase
import com.example.nomad.data.local.entity.FoodTypeEntity
import com.example.nomad.data.local.entity.MainMenuEntity
import com.example.nomad.data.local.entity.ProductEntity
import com.example.nomad.data.network.INomadNetwork
import com.example.nomad.data.network.models.FoodTypeNetwork
import com.example.nomad.data.network.models.MainMenuNetwork
import com.example.nomad.data.network.models.ProductNetwork
import com.example.nomad.data.use_case.FoodTypeConverter
import com.example.nomad.data.use_case.MainMenuConverter
import com.example.nomad.data.use_case.ProductConverter
import com.example.nomad.domain.models.FoodTypeModel
import com.example.nomad.domain.models.MainMenuModel
import com.example.nomad.domain.models.ProductModel

class MenuRepositoryImpl(
    private val nomadDataBase: NomadDataBase,
    private val nomadNetwork: INomadNetwork
) : IMenuRepository {

    override suspend fun fetchMainMenu(context: Context): Result {

        when (val localData = nomadDataBase.getMainMenuEng()) {
            is Result.Success<*> -> {
                val result = mutableListOf<MainMenuModel>()
                val entityData =
                    localData.data as MutableList<MainMenuEntity>
                for (item in entityData) {
                    result.add(MainMenuConverter.entityToModel(item))
                }
                return Result.Success(result)
            }

            is Result.Failure<*> -> {
                when (val networkData = nomadNetwork.getMainMenu()) {
                    is Result.Success<*> -> {
                        val result = mutableListOf<MainMenuModel>()
                        val networkListData =
                            networkData.data as MutableList<MainMenuNetwork>
                        for (item in networkListData) {
                            val tempModel = MainMenuConverter.networkToModel(item, context)
                            result.add(tempModel)
                        }
                        return Result.Success(result)
                    }

                    is Result.Failure<*> -> {
                        return Result.Failure(networkData.error as String)
                    }
                }
            }
        }
    }

    override suspend fun fetchFoodTypeByType(type: String): Result {
        val result = mutableListOf<FoodTypeModel>()
        when (val localData = nomadDataBase.getFoodTypeByType(type)) {
            is Result.Success<*> -> {
                val entityData =
                    localData.data as MutableList<FoodTypeEntity>
                for (item in entityData) {
                    result.add(FoodTypeConverter.entityToModel(item))
                }
                return Result.Success(result)
            }

            is Result.Failure<*> -> {
                return when (val networkData = nomadNetwork.getFoodTypeByType(type)) {
                    is Result.Success<*> -> {
                        val networkListData =
                            networkData.data as MutableList<FoodTypeNetwork>
                        for (item in networkListData) {
                            val tempModel = FoodTypeConverter.networkToModel(item)
                            result.add(tempModel)
                        }
                        Result.Success(result)
                    }

                    is Result.Failure<*> -> {
                        Result.Failure(networkData.error as String)
                    }
                }
            }
        }
    }

    override suspend fun fetchProductByType(type: String, context: Context): Result {
        val result = mutableListOf<ProductModel>()
        when (val localData = nomadDataBase.getProductByType(type)) {
            is Result.Success<*> -> {
                val entityData =
                    localData.data as MutableList<ProductEntity>
                for (item in entityData) {
                    result.add(ProductConverter.entityToModel(item))
                }
                return Result.Success(result)
            }

            is Result.Failure<*> -> {
                when (val networkData = nomadNetwork.getProductByType(type)) {
                    is Result.Success<*> -> {
                        val networkListData =
                            networkData.data as MutableList<ProductNetwork>
                        for (item in networkListData) {
                            val tempModel = ProductConverter.networkToModel(item, context)
                            result.add(tempModel)
                        }
                        return Result.Success(result)
                    }

                    is Result.Failure<*> -> {
                        return Result.Failure(networkData.error as String)
                    }
                }
            }
        }
    }

    override suspend fun insertMainMenuDataBase(mainMenuEntity: MainMenuEntity) {
        nomadDataBase.insertMainMenu(mainMenuEntity)
    }

    override suspend fun insertFoodTypeDataBase(foodTypeEntity: FoodTypeEntity) {
        nomadDataBase.insertFoodType(foodTypeEntity)
    }

    override suspend fun insertProductDataBase(productEntity: ProductEntity) {
        nomadDataBase.insertProduct(productEntity)
    }


    override suspend fun fetchProduct(context: Context): Result {
        val result = mutableListOf<ProductModel>()
        when (val localData = nomadDataBase.getAllProducts()) {
            is Result.Success<*> -> {
                val entityData =
                    localData.data as MutableList<ProductEntity>
                for (item in entityData) {
                    result.add(ProductConverter.entityToModel(item))
                }
                return Result.Success(result)
            }

            is Result.Failure<*> -> {
                when (val networkData = nomadNetwork.getAllProducts()) {
                    is Result.Success<*> -> {
                        val networkListData =
                            networkData.data as MutableList<ProductNetwork>
                        for (item in networkListData) {
                            val tempModel = ProductConverter.networkToModel(item, context)
                            result.add(tempModel)
                            nomadDataBase.insertProduct(ProductConverter.modelToEntity(tempModel))
                        }
                        return Result.Success(result)
                    }

                    is Result.Failure<*> -> {
                        return Result.Failure(networkData.error as String)
                    }
                }
            }
        }
    }

    override suspend fun fetchProductByPattern(
        pattern: String
    ): MutableList<ProductModel> {
        val result = mutableListOf<ProductModel>()
        when (val localData = nomadDataBase.getProductByPattern(pattern)) {
            is Result.Success<*> -> {
                val entityData =
                    localData.data as MutableList<ProductEntity>
                for (item in entityData) {
                    result.add(ProductConverter.entityToModel(item))
                }
            }

            is Result.Failure<*> -> {

            }
        }
        return result
    }

    override suspend fun fetchProductByID(id: Long, context: Context): Result {
        return when (val localData = nomadDataBase.getProductByID(id)) {
            is Result.Success<*> -> {
                val entityData = localData.data as ProductEntity
                Result.Success(ProductConverter.entityToModel(entityData))
            }

            is Result.Failure<*> -> {
                Result.Failure("Didn't find any product")
            }
        }
    }

    //    Used for inserting data to Firestore
    override suspend fun insert() {
        nomadNetwork.insertProduct()
    }
}