package com.example.nomad.data.network

import com.example.nomad.additional.Result
import com.example.nomad.data.network.models.FoodTypeNetwork
import com.example.nomad.data.network.models.MainMenuNetwork
import com.example.nomad.data.network.models.ProductNetwork
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NomadNetwork(
) : INomadNetwork {

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
                                "MainMenuEnglish/" + document.id,
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
                        val product = ProductNetwork(
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
                        list.add(product)
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.Failure(exception.message))
                }
        }
    }

    override suspend fun getProductByType(id: String): Result {
        return suspendCoroutine { continuation ->
            val list = mutableListOf<ProductNetwork>()
            val foodTypeRef = Firebase.firestore.collection("FoodTypeEnglish").document(id)
            Firebase.firestore.collection("ProductEnglish")
                .orderBy("id", Query.Direction.ASCENDING)
                .whereEqualTo("food_type", foodTypeRef)
                .get()
                .addOnSuccessListener { resultCollection ->
                    for (product in resultCollection) {
                        val product = ProductNetwork(
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
                        list.add(product)
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.Failure(exception.message))
                }
        }
    }

    override suspend fun insertProduct() {
        val list = listOf<ProductNetwork2>(
            ProductNetwork2(
                id = 351,
                name_rus = "Домашняя сметана",
                name_kaz = "Үйдегідей әзірленген қаймақ",
                name_eng = "Homemade sour cream",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 300,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 352,
                name_rus = "Чесночный",
                name_kaz = "Сарымсақ",
                name_eng = "Garlic",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 250,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 353,
                name_rus = "Тар-тар",
                name_kaz = "Тар-тар",
                name_eng = "Tar-tar",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 250,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 354,
                name_rus = "BBQ",
                name_kaz = "BBQ",
                name_eng = "BBQ",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 300,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 355,
                name_rus = "Sweetchili",
                name_kaz = "Sweetchili",
                name_eng = "Sweetchili",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 300,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 356,
                name_rus = "Майонез",
                name_kaz = "Майонез",
                name_eng = "Mayonnaise",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 200,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 357,
                name_rus = "Кетчуп",
                name_kaz = "Кетчуп",
                name_eng = "Ketchup",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 200,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 358,
                name_rus = "Сырный",
                name_kaz = "Ірімшік",
                name_eng = "Cheese",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 300,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 359,
                name_rus = "Сацебели",
                name_kaz = "Сацебели",
                name_eng = "Satsebeli",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 300,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            ),
            ProductNetwork2(
                id = 360,
                name_rus = "Табаско",
                name_kaz = "Табаско",
                name_eng = "Tabasco",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 400,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/3fAERQgUjVHoTJB7Kn3o")
            )
        )

        for (data in list) {
            Firebase.firestore.collection("ProductEnglish")
                .add(data)
        }

    }
}

data class ProductNetwork2(
    val id: Long,
    val name_eng: String,
    val name_rus: String,
    val name_kaz: String,
    val overview_eng: String,
    val overview_rus: String,
    val overview_kaz: String,
    val price: Long,
    val image: String,
    val food_type: DocumentReference
)