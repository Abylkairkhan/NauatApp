package com.example.nomad.data.network

import com.example.nomad.additional.Result
import com.example.nomad.data.network.models.FoodTypeNetwork
import com.example.nomad.data.network.models.MainMenuNetwork
import com.example.nomad.data.network.models.ProductNetwork
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NomadNetwork : INomadNetwork {

    override suspend fun getMainMenu(): Result {
        return suspendCoroutine { continuation ->
            val list = mutableListOf<MainMenuNetwork>()
            Firebase.firestore.collection("MainMenuEnglish")
                .orderBy("id", Query.Direction.ASCENDING)
                .get(Source.SERVER)
                .addOnSuccessListener { resultCollection ->
                    for (document in resultCollection) {
                        val mainMenuItem =
                            MainMenuNetwork(
                                document.get("id") as Long,
                                document.id,
                                document.get("name_eng") as String,
                                document.get("name_rus") as String,
                                document.get("name_kaz") as String,
                                document.get("image") as String
                            )
                        list.add(mainMenuItem)
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.Failure(exception.message))
                }
        }
    }

    override suspend fun getFoodTypeByType(documentID: String): Result {
        return suspendCoroutine { continuation ->
            val list = mutableListOf<FoodTypeNetwork>()
            val menuTypeReference =
                Firebase.firestore.collection("MainMenuEnglish").document(documentID)
            Firebase.firestore.collection("FoodTypeEnglish")
                .orderBy("id", Query.Direction.ASCENDING)
                .whereEqualTo("menu_type", menuTypeReference)
                .get(Source.SERVER)
                .addOnSuccessListener { resultCollection ->
                    for (document in resultCollection) {
                        val foodTypeItem = FoodTypeNetwork(
                            document.get("id") as Long,
                            document.id,
                            document.get("name_eng") as String,
                            document.get("name_rus") as String,
                            document.get("name_kaz") as String,
                            document.get("menu_type") as DocumentReference
                        )
                        list.add(foodTypeItem)
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.Failure(exception.message))
                }
        }
    }

    override suspend fun getProductByType(foodTypeID: String): Result {
        return suspendCoroutine { continuation ->
            val list = mutableListOf<ProductNetwork>()
            val foodTypeRef = Firebase.firestore.collection("FoodTypeEnglish").document(foodTypeID)
            Firebase.firestore.collection("ProductEnglish")
                .orderBy("id", Query.Direction.ASCENDING)
                .whereEqualTo("food_type", foodTypeRef)
                .get()
                .addOnSuccessListener { resultCollection ->
                    for (product in resultCollection) {
                        val productTemp = ProductNetwork(
                            product.get("id") as Long,
                            product.get("name_eng") as String,
                            product.get("name_rus") as String,
                            product.get("name_kaz") as String,
                            product.get("overview_eng") as String,
                            product.get("overview_rus") as String,
                            product.get("overview_kaz") as String,
                            product.get("image") as String,
                            product.get("price") as Long,
                            product.get("food_type") as DocumentReference
                        )
                        list.add(productTemp)
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.Failure(exception.message))
                }
        }
    }

    override suspend fun getFoodType(): Result {
        return suspendCoroutine { continuation ->
            val list = mutableListOf<FoodTypeNetwork>()
            Firebase.firestore.collection("FoodTypeEnglish")
                .orderBy("id", Query.Direction.ASCENDING)
                .get(Source.SERVER)
                .addOnSuccessListener { resultCollection ->
                    for (document in resultCollection) {
                        val foodTypeItem = FoodTypeNetwork(
                            document.get("id") as Long,
                            document.id,
                            document.get("name_eng") as String,
                            document.get("name_rus") as String,
                            document.get("name_kaz") as String,
                            document.get("menu_type") as DocumentReference
                        )
                        list.add(foodTypeItem)
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.Failure(exception.message))
                }
        }
    }

    override suspend fun getAllProducts(): Result {
        return suspendCoroutine { continuation ->
            val list = mutableListOf<ProductNetwork>()
            Firebase.firestore.collection("ProductEnglish")
                .orderBy("id", Query.Direction.ASCENDING)
                .get(Source.SERVER)
                .addOnSuccessListener { resultCollection ->
                    for (product in resultCollection) {
                        val productTemp = ProductNetwork(
                            product.get("id") as Long,
                            product.get("name_eng") as String,
                            product.get("name_rus") as String,
                            product.get("name_kaz") as String,
                            product.get("overview_eng") as String,
                            product.get("overview_rus") as String,
                            product.get("overview_kaz") as String,
                            product.get("image") as String,
                            product.get("price") as Long,
                            product.get("food_type") as DocumentReference
                        )
                        list.add(productTemp)
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.Failure(exception.message))
                }
        }
    }

    override suspend fun getPercentageForServe(): Result {
        return suspendCoroutine { continuation ->
            Firebase.firestore.collection("Percentage").document("Serve")
                .get(Source.SERVER)
                .addOnSuccessListener {
                    continuation.resume(Result.Success(it.get("amount") as Long))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.Failure(exception.message))
                }
        }
    }

    override suspend fun insertProduct() {

    }
}