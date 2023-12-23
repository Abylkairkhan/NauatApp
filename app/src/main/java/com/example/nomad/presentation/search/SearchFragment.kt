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
import com.example.nomad.domain.use_case.LanguageController
import com.example.nomad.presentation.adapters.ProductAdapter
import com.example.nomad.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment(), ProductAdapter.Listener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModel<MainViewModel>()
    private var productAdapter: ProductAdapter = ProductAdapter(this@SearchFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchProduct(requireContext())
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
                    viewModel.fetchProductByPattern(newText)
                } else {
                    viewModel.fetchProduct(requireContext())
                }

                return true
            }
        })
    }

    private fun observeProductList() {
        viewModel.productList.observe(viewLifecycleOwner) {
            with(binding) {
                productAdapter.setData(viewModel.productList.value!!)
                RecyclerView.adapter = productAdapter
                RecyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
            }
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