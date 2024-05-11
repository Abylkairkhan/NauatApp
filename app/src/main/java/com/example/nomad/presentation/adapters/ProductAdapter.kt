package com.example.nomad.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nomad.databinding.ProductItemBinding
import com.example.nomad.databinding.ProductItemWithoutImgBinding
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.usecase.LanguageController
import com.example.nomad.domain.usecase.ProductListManager


const val CARD_WITH_IMAGE = 0
const val CARD_WITHOUT_IMAGE = 1

class ProductAdapter(
    val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ProductDiffUtil(
        private val oldList: List<ProductModel>,
        private val newList: List<ProductModel>,
        private val languageChanged: Boolean
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            if (languageChanged) false
            else oldList[oldItemPosition] == newList[newItemPosition]

    }

    private var productList: List<ProductModel> = mutableListOf()

    fun synchronizeCartWithProductList() {
        val cartItems = ProductListManager.getCartItems()

        productList.forEachIndexed { productListPosition, product ->
            // Check if the product is in the cartItems
            val isProductInCart = cartItems.any { it.id == product.id }

            if (isProductInCart) {
                // If the product is in the cart, update countInBucket if needed
                cartItems.find { it.id == product.id }?.let { cartProduct ->
                    if (product.countInBucket != cartProduct.countInBucket) {
                        product.countInBucket = cartProduct.countInBucket
                        notifyItemChanged(productListPosition)
                    }
                }
            } else if (product.countInBucket >= 1) {
                // If the product is not in the cart and countInBucket is >= 1, reset to 0
                product.countInBucket = 0
                notifyItemChanged(productListPosition)
            }
        }
    }

    fun setData(newProductList: List<ProductModel>, languageChanged: Boolean = false) {
        val diffUtil = ProductDiffUtil(productList, newProductList, languageChanged)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        productList = newProductList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ProductWithImage(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {

            binding.productContainer.setOnClickListener {
                listener.onItemClick(product, position)
            }

            with(binding) {
                title.text = LanguageController.getProductLanguage(product, true)
                description.text = LanguageController.getProductLanguage(product, false)
                price.text = product.price.toString() + "₸"
                image.load(product.image)
                count.text = product.countInBucket.toString()

                plusBtn.setOnClickListener {
                    product.countInBucket += 1
                    ProductListManager.addToCart(product)
                    listener.onButtonClick()
                    notifyItemChanged(position)
                }

                minusBtn.setOnClickListener {
                    product.countInBucket -= 1
                    ProductListManager.removeFromCart(product)
                    listener.onButtonClick()
                    notifyItemChanged(position)
                }

                if (product.countInBucket > 0) {
                    minusBtn.visibility = View.VISIBLE
                    count.visibility = View.VISIBLE
                } else {
                    minusBtn.visibility = View.GONE
                    count.visibility = View.GONE
                }
            }
        }
    }

    inner class ProductWithOutImage(private val binding: ProductItemWithoutImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {

            binding.productContainer.setOnClickListener {
                listener.onItemClick(product, position)
            }

            with(binding) {
                title.text = LanguageController.getProductLanguage(product, true)
                description.text = LanguageController.getProductLanguage(product, false)
                price.text = product.price.toString() + "₸"
                count.text = product.countInBucket.toString()

                plusBtn.setOnClickListener {
                    product.countInBucket += 1
                    ProductListManager.addToCart(product)
                    listener.onButtonClick()
                    notifyItemChanged(position)
                }

                minusBtn.setOnClickListener {
                    product.countInBucket -= 1
                    ProductListManager.removeFromCart(product)
                    listener.onButtonClick()
                    notifyItemChanged(position)
                }

                if (product.countInBucket > 0) {
                    minusBtn.visibility = View.VISIBLE
                    count.visibility = View.VISIBLE
                } else {
                    minusBtn.visibility = View.GONE
                    count.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (productList[position].image != null) {
            CARD_WITH_IMAGE
        } else {
            CARD_WITHOUT_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CARD_WITH_IMAGE) {
            val binding =
                ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ProductWithImage(binding)
        } else {
            val binding = ProductItemWithoutImgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ProductWithOutImage(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == CARD_WITH_IMAGE) {
            (holder as ProductWithImage).bind(productList[position], position)
        } else {
            (holder as ProductWithOutImage).bind(productList[position], position)
        }
    }

    override fun getItemCount(): Int =
        productList.size


    interface Listener {
        fun onButtonClick()

        fun onItemClick(product: ProductModel, position: Int)
    }
}