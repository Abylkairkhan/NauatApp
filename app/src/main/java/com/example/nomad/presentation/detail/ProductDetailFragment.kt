package com.example.nomad.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.nomad.databinding.FragmentProductDetailBinding
import com.example.nomad.domain.usecase.LanguageController
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel by viewModel<ProductDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchProductByID(args.productId.toInt(), requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        backButtonClick()
        observeProductAndFillView()

        return binding.root
    }

    private fun backButtonClick() {
        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    private fun observeProductAndFillView() {
        viewModel.product.observe(viewLifecycleOwner) { product ->
            with(binding) {
                if (product.image != null) {
                    productImage.setImageBitmap(product.image)
                    productImage.scaleType = ImageView.ScaleType.CENTER_CROP
                }
                productName.text = LanguageController.getProductLanguage(product, true)
                toolbarProductName.text = LanguageController.getProductLanguage(product, true)
                productOverview.text = LanguageController.getProductLanguage(product, false)
                productPrice.text = product.price.toString() + "₸"
            }
        }
    }

}