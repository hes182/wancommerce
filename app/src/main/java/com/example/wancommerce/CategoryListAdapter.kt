package com.example.wancommerce

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wancommerce.data.ProductModel
import com.example.wancommerce.databinding.ListProductitemBinding

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private var lData = ArrayList<ProductModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryList(lData: ArrayList<ProductModel>, oldCount: Int, lDataSize: Int) {
        this.lData.clear()
        this.lData.addAll(lData)
        notifyDataSetChanged()
        notifyItemRangeInserted(oldCount, lDataSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ListProductitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = lData.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        return holder.Bind(lData[position])
    }

    class CategoryViewHolder(
        private val binding: ListProductitemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun Bind(data: ProductModel) {
            binding.tvvlPrice.text = "Rp ${data.price}"
            binding.tvvlTitle.text = data.title
            binding.tvvlBrancd.text = "${data.brand} / ${data.category}"
            Glide.with(itemView.context)
                .load(data.thumbnail)
                .into(binding.imgThumbnail)
        }
    }

}