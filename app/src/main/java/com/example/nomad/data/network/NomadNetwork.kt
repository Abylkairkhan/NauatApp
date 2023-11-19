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
                id = 81,
                name_rus = "Чечевичный крем-суп",
                name_kaz = "Кілегейлі жасымық сорпасы",
                name_eng = "Lentil cream soup",
                overview_rus = "Крупа чечвицы, грисины, лимон.",
                overview_kaz = "Жасымық жармасы, гриссини, лимон.",
                overview_eng = "Cereals of lentils, gritsins, lemon.",
                image = "",
                price = 1590,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 82,
                name_rus = "Грибной крем-суп",
                name_kaz = "Кілегейлі саңырауқұлақ сорпасы",
                name_eng = "Mushroom cream soup",
                overview_rus = "Шампиньоны, белые грибы с трюфельным ароматом",
                overview_kaz = "Шампиньондар, трюфельдің хош иісі бар ақ саңырауқұлақтар",
                overview_eng = "Mushrooms, porcini mushrooms with truffle aroma",
                image = "",
                price = 1590,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 83,
                name_rus = "Рамен с креветками",
                name_kaz = "Асшаян қосылған рамен",
                name_eng = "Ramen with shrimps",
                overview_rus = "Японский острый пикантный суп, подается с салатом чимчи.",
                overview_kaz = "Жапондық ащы сорпа чимчи салатымен бірге беріледі.",
                overview_eng = "Japanese spicy spicy soup, served with chimchi salad.",
                image = "",
                price = 1990,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 84,
                name_rus = "Рамен с курицей",
                name_kaz = "Балапан етінен әзірленген рамен",
                name_eng = "Ramen with chicken",
                overview_rus = "Японский острый пикантный суп, подается с салатом чимчи.",
                overview_kaz = "Жапондық ащы сорпа чимчи салатымен бірге беріледі.",
                overview_eng = "Japanese spicy spicy soup, served with chimchi salad.",
                image = "",
                price = 1990,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 85,
                name_rus = "Рамен с курицей и сыром чеддер",
                name_kaz = "Балапан еті және чеддер ірімшігінен әзірленген рамен",
                name_eng = "Ramen with chicken and cheddar cheese",
                overview_rus = "Японский острый пикантный суп, подается с салатом чимчи.",
                overview_kaz = "Жапондық ащы ащы сорпа чимчи салатымен бірге беріледі.",
                overview_eng = "Japanese spicy spicy soup, served with chimchi salad.",
                image = "",
                price = 1990,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 86,
                name_rus = "Рамен с телятиной",
                name_kaz = "Бұзау қабырғаларынан әзірленген рамен",
                name_eng = "Ramen with veal",
                overview_rus = "Японский острый пикантный суп, подается с салатом чимчи.",
                overview_kaz = "Жапондық ащы сорпа чимчи салатымен бірге беріледі.",
                overview_eng = "Japanese spicy spicy soup, served with chimchi salad.",
                image = "",
                price = 1990,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 87,
                name_rus = "Том-ям  Тхале",
                name_kaz = "Том-ям  Тхале",
                name_eng = "Tom-yam Thale",
                overview_rus = "Японский суп с морепродуктами тигровые креветки, мидий, филе судака, шампиньоны и с кокосовым молоком",
                overview_kaz = "Жолақты теңіз асшаяндары, мидиялар, көксерке филесі, шампиньондар және кокос сүті қосылған жапон сорпасы",
                overview_eng = "Japanese seafood soup with tiger prawns, mussels, pike perch fillet, champignons and coconut milk",
                image = "",
                price = 2290,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 88,
                name_rus = "Том-ям Кунг",
                name_kaz = "Том-ям Кунг",
                name_eng = "Tom-yam Kung",
                overview_rus = "Японский суп с креветками, кокосовое молоко, шампиньоны",
                overview_kaz = "Асшаян, кокос сүті, саңырауқұлақтар қосылған жапон сорпасы",
                overview_eng = "Japanese shrimp soup, coconut milk, champignons",
                image = "",
                price = 2490,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 89,
                name_rus = "Окрошка",
                name_kaz = "Көк сорпа",
                name_eng = "Okroshka",
                overview_rus = "На домашнем кефире (сезонный)",
                overview_kaz = "Үй айранында әзірленген (маусымдық)",
                overview_eng = "On homemade kefir (seasonal)",
                image = "",
                price = 1390,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 90,
                name_rus = "Кукси",
                name_kaz = "Кукси",
                name_eng = "Cooksey",
                overview_rus = "Корейское холодное блюдо – говядина, лапша, яичный омлет, маринованные помидоры, огурцы, кунжут и бульон кукси-мури.",
                overview_kaz = "Кәрістердің салқын тағамы – сиыр еті, кеспе, жұмыртқа омлеті, маринадталған қызанақ, қияр, күнжіт және кукси-мури сорпасы.",
                overview_eng = "Korean cold dish - beef, noodles, egg omelet, pickled tomatoes, cucumbers, sesame and kuksi-muri broth.",
                image = "",
                price = 1390,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 91,
                name_rus = "Суп харчо",
                name_kaz = "Харчо сорпасы",
                name_eng = "Kharcho soup",
                overview_rus = "Говядина, прохладный бульон, помидоры, рис приготовленные по грузинский, подаётся с хлебом шоти",
                overview_kaz = "Сиыр еті, салқын сорпа, қызанақ, грузин стилінде пісірілген күріш, Шоти нанымен бірге беріледі",
                overview_eng = "Beef, cool broth, tomatoes, rice cooked in Georgian, served with shoti bread",
                image = "",
                price = 1590,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 92,
                name_rus = "Домашние пельмени",
                name_kaz = "Үйдегідей әзірленген тұшпара",
                name_eng = "Homemade dumplings",
                overview_rus = "Подаются с домашней сметаной",
                overview_kaz = "Үйдегі әзірленген қаймақпен бірге беріледі",
                overview_eng = "Served with homemade sour cream",
                image = "",
                price = 1290,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 93,
                name_rus = "Голубцы",
                name_kaz = "Голубцы",
                name_eng = "Golubtsy",
                overview_rus = "Дуэт перцев с ароматной начинкой из говядины (сезонный)",
                overview_kaz = "Хош иісті сиыр еті және бұрыш «дуэті» (маусымдық)",
                overview_eng = "Duet of peppers with fragrant beef filling (seasonal)",
                image = "",
                price = 1490,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 94,
                name_rus = "Солянка",
                name_kaz = "Ащы сорпа",
                name_eng = "Solyanka",
                overview_rus = "Сбор мяса (говядина, солённые огурцы, сосиски, охотничьи колбаски, маслины оливки) подается с домашней сметаной и долками лимона",
                overview_kaz = "Түрлі ет (сиыр еті, маринадталған қияр, шұжықтар, аңшылық шұжықтар, қара зәйтүн) үйдегідей әзірленген қаймақ және лимон тілімдерімен беріледі",
                overview_eng = "Meat picking (beef, pickles, sausages, hunting sausages, olives) is served with homemade sour cream and lemon slices",
                image = "",
                price = 1390,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 95,
                name_rus = "Царская уха с судаком",
                name_kaz = "Көксерке қосылған «Царская уха»",
                name_eng = "Tsar's ear with a zander",
                overview_rus = "Ароматная и наваристая уха – «царская» иначе и не скажешь отменное – первое блюдо приготовленное специально для вас",
                overview_kaz = "Хош иісті және нәрлі балық сорпасы – патшаларға арналған – сіз үшін арнайы дайындалған бірінші тағам.",
                overview_eng = "Fragrant and fragrant ear - \"royal\" otherwise and you can't say excellent - the first dish prepared especially for you",
                image = "",
                price = 1590,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 96,
                name_rus = "Борщ",
                name_kaz = "Борщ",
                name_eng = "Borsch",
                overview_rus = "Вкусный и наваристый суп со свеклой и капустой, подается с домашней сметаной и зеленью!",
                overview_kaz = "Үйдегідей әзірленген қаймақ және аскөкпен бірге ұсынылатын қызылша мен қырыққабат қосылған дәмді сорпа!",
                overview_eng = "Delicious and rich soup with beets and cabbage, served with homemade sour cream and herbs!",
                image = "",
                price = 1290,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 97,
                name_rus = "Суп лапша по домашнему",
                name_kaz = "Үйдегідей әзірленген кеспе сорпасы",
                name_eng = "Homemade noodle soup",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 1090,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 98,
                name_rus = "Рамен сырный",
                name_kaz = "Ірімшік қосылған рамен",
                name_eng = "Cheese ramen",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 1990,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 99,
                name_rus = "Сырный суп с цыпленком и шампиньонами",
                name_kaz = "Балапан еті мен қозықұйрық қосылған ірімшік сорпа",
                name_eng = "Cheese soup with chicken and mushrooms",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 1590,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),
            ProductNetwork2(
                id = 100,
                name_rus = "Сливочный суп с лососем и овощами",
                name_kaz = "Қызыл балық пен көкөніс қосылған кілегей сорпа",
                name_eng = "Creamy soup with salmon and vegetables",
                overview_rus = "",
                overview_kaz = "",
                overview_eng = "",
                image = "",
                price = 1990,
                food_type = FirebaseFirestore.getInstance()
                    .document("FoodTypeEnglish/nT9Ikj88wR0Xr8heyT5n")
            ),


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