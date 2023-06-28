package com.jufarangoma.melitests.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jufarangoma.melitests.databinding.ItemAttributeBinding
import com.jufarangoma.melitests.domain.entities.Attribute

class AttributesAdapter : RecyclerView.Adapter<AttributesAdapter.AttributesViewHolder>() {

    private val arrayOfAttributes: ArrayList<Attribute> = arrayListOf()

    fun setList(attributes: List<Attribute>){
        arrayOfAttributes.clear()
        arrayOfAttributes.addAll(attributes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributesViewHolder {
        return AttributesViewHolder(
            ItemAttributeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = arrayOfAttributes.size

    override fun onBindViewHolder(holder: AttributesViewHolder, position: Int) {
        val item = arrayOfAttributes[position]
        holder.bind(item)
    }

    inner class AttributesViewHolder(private val binding: ItemAttributeBinding) :
        ViewHolder(binding.root) {

        fun bind(attribute: Attribute) {
            with(binding) {
                txvAttributeName.text = attribute.name
                txvAttributeValue.text = attribute.valueName
            }
        }
    }
}
