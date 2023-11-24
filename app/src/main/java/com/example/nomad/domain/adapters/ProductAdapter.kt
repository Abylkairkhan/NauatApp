package com.example.nomad.domain.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nomad.databinding.ProductItemBinding
import com.example.nomad.databinding.ProductItemWithoutImgBinding
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.use_case.BillCounter
import com.example.nomad.domain.use_case.LanguageController
import com.example.nomad.domain.use_case.ProductListManager


const val CARD_WITH_IMAGE = 0
const val CARD_WITHOUT_IMAGE = 1
const val CARD_DIVIDER = 2

class ProductAdapter(
    val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productList: List<ProductModel> = mutableListOf()

    fun updateItems() {
        notifyDataSetChanged()
    }

    fun setItems(data: List<ProductModel>) {
        productList = data
        notifyDataSetChanged()
    }

    inner class ProductWithImage(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {
            with(binding) {
                title.text = LanguageController.getProductLanguage(product, true)
                description.text = LanguageController.getProductLanguage(product, false)
                price.text = product.price.toString() + "₸"
                image.load(product.image)
                count.text = product.countInBucket.toString()

                plusBtn.setOnClickListener {
                    product.countInBucket +=1
                    ProductListManager.addToCart(product)
                    listener.onClick()
                    notifyItemChanged(position)
                }

                minusBtn.setOnClickListener {
                    product.countInBucket -=1
                    ProductListManager.removeFromCart(product)
                    listener.onClick()
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
            with(binding) {
                title.text = LanguageController.getProductLanguage(product, true)
                description.text = LanguageController.getProductLanguage(product, false)
                price.text = product.price.toString() + "₸"
                count.text = product.countInBucket.toString()

                plusBtn.setOnClickListener {
                    product.countInBucket +=1
                    ProductListManager.addToCart(product)
                    listener.onClick()
                    notifyItemChanged(position)
                }

                minusBtn.setOnClickListener {
                    product.countInBucket -=1
                    ProductListManager.removeFromCart(product)
                    listener.onClick()
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

    override fun getItemCount(): Int {
        return productList.size
    }

    interface Listener {
        fun onClick()
    }
}