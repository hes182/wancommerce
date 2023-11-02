package com.example.wancommerce

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wancommerce.data.ProductModel
import com.example.wancommerce.databinding.ListProductitemBinding
import retrofit2.Response
import java.util.Locale

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewModel>(), Filterable {
    private var dataList = ArrayList<ProductModel>()
    private var searchDataLis = ArrayList<ProductModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listProduct: ArrayList<ProductModel>, oldCount: Int, listProdSize: Int) {
        this.dataList.clear()
        this.dataList.addAll(listProduct)
        notifyDataSetChanged()
        notifyItemRangeInserted(oldCount, listProdSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val binding = ListProductitemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewModel(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        holder.Bind(dataList[position])
    }




    class ViewModel(
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

    override fun getFilter(): Filter {
       return object : Filter() {
           override fun performFiltering(contsrain: CharSequence?): FilterResults {
               val charSearch = contsrain.toString()
               if (!charSearch.isEmpty() || !charSearch.isNullOrEmpty()) {
                   val resultlist = ArrayList<ProductModel>()
                   for (row in dataList) {
                       if (row.title.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) ||
                           row.title.uppercase(Locale.ROOT).contains(charSearch.uppercase(Locale.ROOT)) ||
                           row.category.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) ||
                           row.category.uppercase(Locale.ROOT).contains(charSearch.uppercase(Locale.ROOT))) {
                           resultlist.add(row)

                       }
                   }
                   searchDataLis = resultlist
               }
               val filterresult = FilterResults()
               filterresult.values = searchDataLis
               return filterresult
           }

           @Suppress("UNCHECKED_CAST")
           @SuppressLint("NotifyDataSetChanged")
           override fun publishResults(p0: CharSequence?, results: FilterResults?) {
               dataList = results?.values as ArrayList<ProductModel>
               notifyDataSetChanged()
               notifyItemRangeInserted(0, dataList.size)
           }

       }
    }

}