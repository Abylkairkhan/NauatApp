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

class BucketAdapter(
    val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class BucketDiffUtil(
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
            else
                oldList[oldItemPosition].countInBucket == newList[newItemPosition].countInBucket


    }

    private var bucketProductList: MutableList<ProductModel> = mutableListOf()

    fun setData(newBucketItemList: List<ProductModel>, languageChanged: Boolean = false) {
        val diffUtil = BucketDiffUtil(bucketProductList, newBucketItemList, languageChanged)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        bucketProductList =
            if (newBucketItemList.isEmpty()) mutableListOf() else newBucketItemList as MutableList<ProductModel>
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ProductWithImage(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {
            with(binding) {

                productContainer.setOnClickListener {
                    listener.onClick(product)
                }

                title.text = LanguageController.getProductLanguage(product, true)
                description.text = LanguageController.getProductLanguage(product, false)
                price.text = product.price.toString() + "₸"
                image.load(product.image)
                count.text = product.countInBucket.toString()
                minusBtn.visibility = View.VISIBLE
                count.visibility = View.VISIBLE

                plusBtn.setOnClickListener {
                    ProductListManager.addToCart(product)
                    notifyItemChanged(position)
                }

                minusBtn.setOnClickListener {
                    ProductListManager.removeFromCart(product)
                    if (product.countInBucket <= 0) {
                        val newTempList = bucketProductList.toMutableList()
                        newTempList.remove(product)
                        setData(newTempList)
                    } else
                        setData(ProductListManager.getCartItems(), true)

                }
            }
        }
    }

    inner class ProductWithOutImage(private val binding: ProductItemWithoutImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel, position: Int) {

            with(binding) {

                productContainer.setOnClickListener {
                    listener.onClick(product)
                }

                title.text = LanguageController.getProductLanguage(product, true)
                description.text = LanguageController.getProductLanguage(product, false)
                price.text = product.price.toString() + "₸"
                count.text = product.countInBucket.toString()
                minusBtn.visibility = View.VISIBLE
                count.visibility = View.VISIBLE

                plusBtn.setOnClickListener {
                    ProductListManager.addToCart(product)
                    notifyItemChanged(position)
                }

                minusBtn.setOnClickListener {
                    ProductListManager.removeFromCart(product)
                    if (product.countInBucket <= 0) {
                        val newTempList = bucketProductList.toMutableList()
                        newTempList.remove(product)
                        setData(newTempList)
                    } else
                        setData(ProductListManager.getCartItems(), true)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (bucketProductList[position].image != null) {
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
            (holder as ProductWithImage).bind(bucketProductList[position], position)
        } else {
            (holder as ProductWithOutImage).bind(bucketProductList[position], position)
        }
    }

    override fun getItemCount(): Int = bucketProductList.size

    interface Listener {
        fun onClick(product: ProductModel)
    }

}