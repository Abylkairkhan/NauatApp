package com.example.nomad.presentation.bucket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nomad.databinding.FragmentBucketBinding
import com.example.nomad.domain.adapters.ProductAdapter
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.use_case.BillCounter
import com.example.nomad.domain.use_case.LanguageController

class BucketFragment : Fragment(), ProductAdapter.AddClickListener {

    private lateinit var binding: FragmentBucketBinding
    private var productAdapter: ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productAdapter = ProductAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBucketBinding.inflate(inflater, container, false)
        binding.orderText.text = getString(LanguageController.getOrder())
        backButton()
        initRecView()

        return binding.root
    }

    private fun backButton() {
        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    private fun initRecView() {
        val data = mutableListOf<ProductModel>()
        BillCounter.getBucketProducts().forEach {
            if (it.countInBucket > 0) {
                data.add(it)
            }
        }
        Log.d("MyLog", "data size: " + data.size.toString())
        productAdapter!!.setSortedItems(data)
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerView.adapter = productAdapter
    }

    override fun onItemPlusClick(product: ProductModel, addToBill: Boolean) {
        BillCounter.setBill(product.price, addToBill)
        initRecView()
    }
}