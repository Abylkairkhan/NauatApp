package com.example.nomad.domain.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.nomad.domain.models.ProductModel

class ProductDiffCallback(
    val oldList: List<ProductModel>,
    val newList: List<ProductModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }
}