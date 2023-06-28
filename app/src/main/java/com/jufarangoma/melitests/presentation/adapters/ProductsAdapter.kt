package com.jufarangoma.melitests.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jufarangoma.melitests.R
import com.jufarangoma.melitests.databinding.ItemProductBinding
import com.jufarangoma.melitests.domain.entities.ProductEntity
import com.jufarangoma.melitests.utils.setImage
import com.jufarangoma.melitests.utils.toMoney

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val arrayListProducts: ArrayList<ProductEntity> = arrayListOf()

    fun setList(products: List<ProductEntity>) {
        arrayListProducts.clear()
        arrayListProducts.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayListProducts.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = arrayListProducts[position]
        holder.bind(item)
    }

    inner class ProductsViewHolder(private val binding: ItemProductBinding) :
        ViewHolder(binding.root) {

        fun bind(productEntity: ProductEntity) {
            with(binding) {
                imvProduct.setImage(productEntity.image)
                txvProductName.text = productEntity.name
                txvProductPrice.text = productEntity.price.toMoney()
                if (productEntity.getDiscount() != 0) {
                    txvProductDiscount.text =
                        txvProductDiscount.context.getString(
                            R.string.product_discount,
                            productEntity.getDiscount()
                        )
                }
                txvProductFee.text = txvProductFee.context.getString(
                    R.string.fee,
                    productEntity.fee.toMoney()
                )
                productEntity.seller?.let {
                    txvProductSeller.text =
                        txvProductSeller.context.getString(R.string.seller, productEntity.seller)
                }
            }
        }
    }
}
