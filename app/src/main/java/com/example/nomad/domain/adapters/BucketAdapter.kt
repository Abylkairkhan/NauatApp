package com.example.nomad.domain.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.nomad.databinding.ProductItemBinding
import com.example.nomad.databinding.ProductItemWithoutImgBinding
import com.example.nomad.domain.models.ProductModel
import com.example.nomad.domain.use_case.BillCounter
import com.example.nomad.domain.use_case.LanguageController

class ProductModelDiffCallback(
    private val oldList: List<ProductModel>,
    private val newList: List<ProductModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

class BucketAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var productList: MutableList<ProductModel> = mutableListOf()

    fun setItems(newList: List<ProductModel>) {
        val diffResult = DiffUtil.calculateDiff(
            ProductModelDiffCallback(productList, newList)
        )
        productList.clear()
        productList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
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
                    BillCounter.setBill(product.price, true)
                    product.countInBucket+=1
                }

                minusBtn.setOnClickListener {
                    BillCounter.setBill(product.price, false)
                    product.countInBucket-=1
                    if (product.countInBucket <= 0) {
                        // Remove the item from the list if count is zero or negative
                        productList.remove(product)
                    }

                    // Notify the adapter of the data change
                    notifyDataSetChanged()
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
                    BillCounter.setBill(product.price, true)
                    product.countInBucket+=1
                }

                minusBtn.setOnClickListener {
                    BillCounter.setBill(product.price, false)
                    product.countInBucket-=1
                    if (product.countInBucket <= 0) {
                        // Remove the item from the list if count is zero or negative
                        productList.remove(product)
                    }

                    // Notify the adapter of the data change
                    notifyDataSetChanged()
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
}