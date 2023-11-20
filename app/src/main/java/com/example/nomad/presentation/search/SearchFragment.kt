package com.example.nomad.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nomad.R
import com.example.nomad.databinding.FragmentSearchBinding
import com.example.nomad.domain.adapters.ProductAdapter
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.use_case.BillCounter
import com.example.nomad.domain.use_case.LanguageController
import com.example.nomad.domain.use_case.ProductListManager


class SearchFragment : Fragment(), ProductAdapter.Listener {

    private lateinit var binding: FragmentSearchBinding
    private var productAdapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        backButton()
        observeProductList()
        filterList()
        language()
        return binding.root
    }

    private fun language() {
        with(binding) {
            searchView.queryHint = getString(LanguageController.getSearch())
            button.text = getString(LanguageController.getSave())
        }
    }

    private fun filterList() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    val sortedList = ProductListManager.getProducts().filter { product ->
                        when(LanguageController.getLanguage()){
                            LanguageController.Language.RUS -> product.nameRus.contains(newText, true)
                            LanguageController.Language.KAZ -> product.nameKaz.contains(newText, true)
                            LanguageController.Language.ENG -> product.nameEng.contains(newText, true)
                        }
                    }
                    productAdapter!!.setSortedItems(sortedList)
                } else {
                    productAdapter!!.setItems()
                }
                return true
            }
        })
    }

    private fun observeProductList() {
        with(binding) {
            productAdapter = ProductAdapter(this@SearchFragment)
            productAdapter!!.setItems()
            RecyclerView.adapter = productAdapter
            RecyclerView.layoutManager =
                LinearLayoutManager(requireContext())
        }
    }

    private fun backButton() {
        binding.button.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.search_to_main)
        }
    }

    override fun onClick() {

    }
}