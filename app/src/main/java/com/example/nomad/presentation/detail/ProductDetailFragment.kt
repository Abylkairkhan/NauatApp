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
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.usecase.LanguageController
import com.example.nomad.domain.usecase.ProductListManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel by viewModel<ProductDetailViewModel>()
    private var product: ProductModel? = null

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

        binding.addToCartBtn.setOnClickListener {
            ProductListManager.addToCart(product!!)
            findProductInBucket()
        }

        binding.plusBtn.setOnClickListener {
            ProductListManager.addToCart(product!!)
            findProductInBucket()
            binding.itemCount.text = product!!.countInBucket.toString()
        }

        binding.minusBtn.setOnClickListener {
            ProductListManager.removeFromCart(product!!)
            binding.itemCount.text = product!!.countInBucket.toString()
        }
    }

    private fun observeProductAndFillView() {
        viewModel.product.observe(viewLifecycleOwner) { productTemp ->

            for (productFor in ProductListManager.getCartItems()) {
                if (productFor.id == productTemp.id) {
                    with(binding) {
                        addToCartBtn.visibility = View.GONE
                        minusBtn.visibility = View.VISIBLE
                        plusBtn.visibility = View.VISIBLE
                        itemCount.visibility = View.VISIBLE
                        itemCount.text = productFor.countInBucket.toString()
                    }
                    product = productFor
                }
            }

            with(binding) {
                if (productTemp.image != null) {
                    productImage.setImageBitmap(productTemp.image)
                    productImage.scaleType = ImageView.ScaleType.CENTER_CROP
                }
                productName.text = LanguageController.getProductLanguage(productTemp, true)
                toolbarProductName.text = LanguageController.getProductLanguage(productTemp, true)
                if (productTemp.overviewRus.isEmpty()) productOverview.visibility = View.GONE
                productOverview.text = LanguageController.getProductLanguage(productTemp, false)
                productPrice.text = productTemp.price.toString() + "₸"
                addToCartBtn.text = getString(LanguageController.getAddToCartLang())
            }

            if (product == null)
                product = productTemp
        }
    }

    private fun findProductInBucket() {
        for (productFor in ProductListManager.getCartItems()) {
            if (productFor.id == product!!.id) {
                with(binding) {
                    addToCartBtn.visibility = View.GONE
                    minusBtn.visibility = View.VISIBLE
                    plusBtn.visibility = View.VISIBLE
                    itemCount.visibility = View.VISIBLE
                    itemCount.text = productFor.countInBucket.toString()
                }
                product = productFor
            }
        }
    }
}