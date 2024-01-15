package com.example.nomad.presentation.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nomad.R
import com.example.nomad.databinding.PagerItemBinding
import com.example.nomad.domain.models.FoodTypeModel
import com.example.nomad.domain.usecase.LanguageController

private var selectedPosition: Int = 0

class PagerItemAdapter(
    private val context: Context,
    private val listener: Listener,
) : RecyclerView.Adapter<PagerItemAdapter.PagerItemHolder>() {

    class PagerItemDiffUtil(
        private val oldList: List<FoodTypeModel>,
        private val newList: List<FoodTypeModel>,
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

    private var foodTypeList: List<FoodTypeModel> = listOf()

    fun setData(newFoodTypeList: List<FoodTypeModel>, languageChanged: Boolean = false) {
        val diffUtil = PagerItemDiffUtil(foodTypeList, newFoodTypeList, languageChanged)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        foodTypeList = newFoodTypeList
        diffResult.dispatchUpdatesTo(this)
    }

    fun selectItem(position: Int) {
        val previousSelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousSelectedPosition)
        notifyItemChanged(selectedPosition)
    }

    class PagerItemHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding: PagerItemBinding = PagerItemBinding.bind(item)

        fun bind(foodType: FoodTypeModel, drawable: Drawable, position: Int, listener: Listener) =
            with(binding) {
//                binding.textView.width
                textView.text = LanguageController.getFoodTypeLanguage(foodType)
                textView.background = drawable

                itemView.setOnClickListener {
                    if (position != selectedPosition) listener.onClick(foodType, position)
                }
            }
    }

    override fun onBindViewHolder(holder: PagerItemHolder, position: Int) {
        if (selectedPosition == position) {
            holder.bind(
                foodTypeList[position],
                AppCompatResources.getDrawable(context, R.drawable.selected_item_bg)!!,
                position,
                listener
            )
        } else {
            holder.bind(
                foodTypeList[position],
                AppCompatResources.getDrawable(context, R.drawable.unselected_item_bg)!!,
                position,
                listener
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerItemHolder {
        return PagerItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return foodTypeList.size
    }

    interface Listener {
        fun onClick(foodType: FoodTypeModel, position: Int)
    }
}