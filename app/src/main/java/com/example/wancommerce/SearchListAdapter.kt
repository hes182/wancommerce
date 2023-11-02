package com.example.wancommerce

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wancommerce.data.ProductModel
import com.example.wancommerce.databinding.ListProductitemBinding

class SearchListAdapter: RecyclerView.Adapter<SearchListAdapter.SearchViewAdapter>() {
    private var lisData = ArrayList<ProductModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchData(lData: ArrayList<ProductModel>, oldCount: Int, lDataSize: Int) {
        this.lisData.clear()
        this.lisData.addAll(lData)
        notifyDataSetChanged()
        notifyItemRangeInserted(oldCount, lDataSize)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewAdapter {
        val binding = ListProductitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewAdapter(binding)
    }

    override fun getItemCount(): Int = lisData.size

    override fun onBindViewHolder(holder: SearchViewAdapter, position: Int) {
        return holder.Bind(lisData[position])
    }

    class SearchViewAdapter(
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