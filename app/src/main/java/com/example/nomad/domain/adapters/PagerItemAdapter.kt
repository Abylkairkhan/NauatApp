package com.example.nomad.domain.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.nomad.R
import com.example.nomad.databinding.PagerItemBinding
import com.example.nomad.domain.models.FoodTypeModel
import com.example.nomad.domain.use_case.LanguageController

private var selectedPosition: Int = 0

class PagerItemAdapter(
    private val context: Context,
    private val listener: Listener,
) : RecyclerView.Adapter<PagerItemAdapter.PagerItemHolder>() {

    private var list: List<FoodTypeModel> = listOf()

    fun setItems(_list: List<FoodTypeModel>) {
        list = _list
        notifyDataSetChanged()
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
                textView.text = LanguageController.getFoodTypeLanguage(foodType)
                textView.background = drawable

                itemView.setOnClickListener {
                    if (position != selectedPosition) listener.onClick(foodType)
                    listener.onItemPosition(position)
                }
            }
    }

    override fun onBindViewHolder(holder: PagerItemHolder, position: Int) {
        if (selectedPosition == position) {
            holder.bind(
                list[position],
                AppCompatResources.getDrawable(context, R.drawable.selected_item_bg)!!,
                position,
                listener
            )
        } else {
            holder.bind(
                list[position],
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
        return list.size
    }

    interface Listener {
        fun onClick(foodType: FoodTypeModel)
        fun onItemPosition(position: Int)
    }
}