package com.example.nomad.domain.use_case

import com.example.nomad.R
import com.example.nomad.domain.models.FoodTypeModel
import com.example.nomad.domain.models.MainMenuModel
import com.example.nomad.domain.models.ProductModel


object LanguageController {

    enum class Language {
        RUS, KAZ, ENG
    }

    private var currentLanguage = Language.RUS

    fun getClear(): Int {
        return when (currentLanguage) {
            Language.RUS -> R.string.clearRus
            Language.KAZ -> R.string.clearKaz
            Language.ENG -> R.string.clearEng
        }
    }

    fun getSave(): Int {
        return when (currentLanguage) {
            Language.RUS -> R.string.saveRus
            Language.KAZ -> R.string.saveKaz
            Language.ENG -> R.string.saveEng
        }
    }

    fun getOrder(): Int {
        return when (currentLanguage) {
            Language.RUS -> R.string.orderRus
            Language.KAZ -> R.string.orderKaz
            Language.ENG -> R.string.orderEng
        }
    }

    fun getSearch(): Int {
        return when (currentLanguage) {
            Language.RUS -> R.string.searchRus
            Language.KAZ -> R.string.searchKaz
            Language.ENG -> R.string.searchEng
        }
    }

    fun getAbbreviation(): Int {
        return when (currentLanguage) {
            Language.RUS -> R.string.rus
            Language.KAZ -> R.string.kaz
            Language.ENG -> R.string.eng
        }
    }

    fun getMainMenuLanguage(item: MainMenuModel): String {
        return when (currentLanguage) {
            Language.RUS -> item.nameRus
            Language.KAZ -> item.nameKaz
            Language.ENG -> item.nameEng
        }
    }

    fun getFoodTypeLanguage(item: FoodTypeModel): String {
        return when (currentLanguage) {
            Language.RUS -> item.nameRus
            Language.KAZ -> item.nameKaz
            Language.ENG -> item.nameEng
        }
    }

    fun getProductLanguage(item: ProductModel, name: Boolean): String {
        return if (name) {
            when (currentLanguage) {
                Language.RUS -> item.nameRus
                Language.KAZ -> item.nameKaz
                Language.ENG -> item.nameEng
            }
        } else {
            when (currentLanguage) {
                Language.RUS -> item.overviewRus
                Language.KAZ -> item.overviewKaz
                Language.ENG -> item.overviewEng
            }
        }
    }

    fun getLanguage(): Language {
        return currentLanguage
    }

    fun setLanguage() {
        currentLanguage = when (currentLanguage) {
            Language.RUS -> Language.KAZ
            Language.KAZ -> Language.ENG
            Language.ENG -> Language.RUS
        }
    }
}