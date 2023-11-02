package com.example.wancommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wancommerce.data.ProductModel
import com.example.wancommerce.data.ResponseModel
import com.example.wancommerce.databinding.ActivityMainBinding
import com.example.wancommerce.di.remote.ApiClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = ProductAdapter()
    private val adapterSearch = SearchListAdapter()
    private val adapterCategory = CategoryListAdapter()
    var dataListSearch = ArrayList<ProductModel>()
    var apiCLient = ApiClient()
    var currentSkip = 0
    var totalData = 0
    var categorylist: Any? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.rcViewProduct.setHasFixedSize(true)
        binding.rcViewProduct.layoutManager = GridLayoutManager(this,2)

        binding.rcViewProduct.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rcViewProduct.canScrollVertically(0)) {
                    if (currentSkip <= totalData) {
                        currentSkip += 10
                        loadData()
                    }
                }
            }
        })
        loadData()

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                loadSearch(searchText)
                return false
            }
        })

        binding.button.setOnClickListener {
            dialogBottom()
        }
    }

    private fun loading() {
        if (currentSkip == 0) {
            if (binding.progresbarLoad.isShown) {
                binding.progresbarLoad.visibility = View.GONE
            } else {
                binding.progresbarLoad.visibility = View.VISIBLE
            }
        } else {
            if (binding.progresbarLoadScroll.isShown) {
                binding.progresbarLoadScroll.visibility = View.GONE
            } else {
                binding.progresbarLoadScroll.visibility = View.VISIBLE
            }
        }
    }

    private fun loadData() {
        loading()
        apiCLient.callGetProductLisReq(currentSkip, object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    if (responseModel != null) {
                        val dataList = responseModel.products
                        val oldCount = dataList.size
                        totalData = responseModel.total
                        adapter.setListData(dataList, oldCount, dataList.size)
                        binding.rcViewProduct.adapter = adapter
                    }
                }
                loading()
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MainActivity, "response: Server Problem", Toast.LENGTH_SHORT).show()
                loading()
            }
        })
    }


    private fun loadSearch(str: String?) {
        loading()
        apiCLient.callSearchProductList(str, object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    if (responseModel != null) {
                        val oldCount = dataListSearch.size
                        totalData = responseModel.total
                        adapterSearch.setSearchData(responseModel.products, oldCount, responseModel.products.size)
                        binding.rcViewProduct.adapter = adapterSearch
                    }
                }
                loading()
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MainActivity, "response: Server Problem", Toast.LENGTH_SHORT).show()
                loading()
            }
        })
    }

    private fun getCaegory() {
        apiCLient.getCategory(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    if (responseModel != null) {
                        categorylist = responseModel
                    }
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MainActivity, "response: Server Problem", Toast.LENGTH_SHORT).show()
                loading()
            }

        })
    }

    private fun dialogBottom() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialogbottom, null)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.show()

        val category = dialog.findViewById<ListView>(R.id.list_category)
        val listCateg = listOf("smartphones",
            "laptops",
            "fragrances",
            "skincare",
            "groceries",
            "home-decoration",
            "furniture",
            "tops",
            "womens-dresses",
            "womens-shoes",
            "mens-shirts",
            "mens-shoes",
            "mens-watches",
            "womens-watches",
            "womens-bags",
            "womens-jewellery",
            "sunglasses",
            "automotive",
            "motorcycle",
            "lighting")
        category?.adapter = ArrayAdapter(dialog.context, android.R.layout.simple_list_item_1, listCateg)

        category?.setOnItemClickListener { adapterView, view, i, l ->
            loadCategoryProduct(listCateg[i])
            dialog.dismiss()
        }
    }

    private fun loadCategoryProduct(category: String) {
        loading()
        apiCLient.getProductCategory(category, object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    if (responseModel != null) {
                        val oldCount = dataListSearch.size
                        totalData = responseModel.total
                        val dataList = responseModel.products
                        adapterCategory.setCategoryList(dataList, oldCount, dataList.size)
                        binding.rcViewProduct.adapter = adapterCategory
                    }
                }
                loading()
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MainActivity, "response: Server Problem", Toast.LENGTH_SHORT).show()
                loading()
            }

        })
    }
}