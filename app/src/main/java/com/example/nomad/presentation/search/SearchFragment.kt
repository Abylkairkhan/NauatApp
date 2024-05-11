package com.example.nomad.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.SearchView.OnCloseListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nomad.databinding.FragmentSearchBinding
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.usecase.LanguageController
import com.example.nomad.presentation.adapters.ProductAdapter
import com.example.nomad.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), ProductAdapter.Listener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<MainViewModel>()
    private var productAdapter: ProductAdapter = ProductAdapter(this@SearchFragment)
    private var lastSearchedText = ""
    private var clickedProductPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchProduct(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.RecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

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

                if (!newText.isNullOrEmpty() && lastSearchedText != newText) {
                    viewModel.fetchProductByPattern(newText)
                } else if (newText == "" && lastSearchedText != newText) {
                    viewModel.fetchProduct(requireContext())
                }
                lastSearchedText = newText ?: ""
                return true
            }
        })

        binding.searchView.setOnCloseListener(object : OnCloseListener {
            override fun onClose(): Boolean {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
                return true
            }

        })
    }

    private fun observeProductList() {
        viewModel.productList.observe(viewLifecycleOwner) {
            with(binding) {
                RecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                productAdapter.setData(viewModel.productList.value!!)
                RecyclerView.scrollToPosition(clickedProductPosition)
                RecyclerView.adapter = productAdapter
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            with(binding) {
                RecyclerView.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                errorTextView.text = getString(LanguageController.getPatternFindError())
            }
        }
    }

    private fun backButton() {
        binding.button.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    override fun onButtonClick() {

    }

    override fun onItemClick(product: ProductModel, position: Int) {
        clickedProductPosition = position
        val action = SearchFragmentDirections.searchToProduct(product.id)
        Navigation.findNavController(requireView()).navigate(action)
    }
}