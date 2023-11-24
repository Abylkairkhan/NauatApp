package com.example.nomad.presentation.bucket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nomad.R
import com.example.nomad.databinding.FragmentBucketBinding
import com.example.nomad.domain.adapters.BucketAdapter
import com.example.nomad.domain.adapters.ProductAdapter
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.use_case.BillCounter
import com.example.nomad.domain.use_case.LanguageController
import com.example.nomad.domain.use_case.ProductListManager

class BucketFragment : Fragment() {

    private lateinit var binding: FragmentBucketBinding
    private var productAdapter: BucketAdapter = BucketAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBucketBinding.inflate(inflater, container, false)
        binding.orderText.text = getString(LanguageController.getOrder())
        binding.ClearButton.text = getString(LanguageController.getClear())
        backButton()
        clearButtonClick()
        initRecView()

        return binding.root
    }

    private fun clearButtonClick() {
        binding.ClearButton.setOnClickListener {
            ProductListManager.clearCart()
            productAdapter.setItems(ProductListManager.getCartItems())
        }
    }

    private fun initRecView() {
        productAdapter.setItems(ProductListManager.getCartItems())
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerView.adapter = productAdapter
    }

    private fun backButton() {
        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.bucket_to_main)
        }
    }
}