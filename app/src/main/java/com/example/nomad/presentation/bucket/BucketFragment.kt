package com.example.nomad.presentation.bucket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nomad.databinding.FragmentBucketBinding
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.usecase.LanguageController
import com.example.nomad.domain.usecase.ProductListManager
import com.example.nomad.presentation.adapters.BucketAdapter

class BucketFragment : Fragment(), BucketAdapter.Listener {

    private lateinit var binding: FragmentBucketBinding
    private var productAdapter: BucketAdapter = BucketAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productAdapter.setData(ProductListManager.getCartItems())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBucketBinding.inflate(inflater, container, false)

        initRecViewAndText()

        backButton()
        clearButtonClick()

        return binding.root
    }

    private fun clearButtonClick() {
        binding.ClearButton.setOnClickListener {
            ProductListManager.clearCart()
            productAdapter.setData(ProductListManager.getCartItems())
        }
    }

    private fun initRecViewAndText() {
        with(binding) {
            RecyclerView.layoutManager = LinearLayoutManager(requireContext())
            RecyclerView.adapter = productAdapter
            orderText.text = getString(LanguageController.getOrder())
            ClearButton.text = getString(LanguageController.getClear())
            servePercent.text =
                getString(LanguageController.getServePercentage()) + " " + ProductListManager.getServePercentage()
                    .toString() + "%"
        }
    }

    private fun backButton() {
        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    override fun onClick(product: ProductModel) {
        val action = BucketFragmentDirections.bucketToProduct(product.id)
        Navigation.findNavController(requireView()).navigate(action)
    }
}