package com.example.advanceenglishdictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advanceenglishdictionary.models.CommonExpressions
import com.example.advanceenglishdictionary.databinding.ItemCommonExpressionsBinding

class CommonExpressionsAdapter(
    private val expressions: List<CommonExpressions>,
    private val onItemClick: (CommonExpressions) -> Unit
) : RecyclerView.Adapter<CommonExpressionsAdapter.ExpressionViewHolder>() {

    inner class ExpressionViewHolder(val binding: ItemCommonExpressionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpressionViewHolder {
        val binding = ItemCommonExpressionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpressionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpressionViewHolder, position: Int) {
        val expression = expressions[position]
        holder.binding.textViewCategoriesName.text = expression.categoryName
        holder.binding.textViewCategoriesDescription.text = expression.categoryDescription

        // Make the item clickable
        holder.binding.root.setOnClickListener {
            onItemClick(expression)
        }
    }

    override fun getItemCount(): Int = expressions.size

}