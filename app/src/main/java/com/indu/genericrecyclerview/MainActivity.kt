package com.indu.genericrecyclerview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.indu.genericrecyclerview.databinding.ActivityMainBinding
import com.indu.genericrecyclerview.databinding.ItemTvBinding
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Indu Bala on 22/10/21.
 */

class MainActivity : AppCompatActivity() {
    private var adapter: RecyclerViewGenricAdapter<String, ItemTvBinding>? = null
    private var adapterCustom: RecyclerViewGenricAdapter<CustomObject, ItemTvBinding>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            callCustomdapter(recyclerView)
            editSearch.doOnTextChanged { text, _, _, _ ->
                adapterCustom?.filter?.filter(text)
            }
        }


    }

    private fun callAdapter(recyclerView: RecyclerView) {
        val list = ArrayList<String>()
        list.add("America")
        list.add("Zoo")
        list.add("Canada")
        list.add("Pen")
        list.add("Kitkat")

        list.sortByDescending { it }
        adapter =  object : RecyclerViewGenricAdapter<String,ItemTvBinding>(
            list,
            R.layout.item_tv, object :
                RecyclerCallback<ItemTvBinding, String> {
                override fun bindData(
                    binder: ItemTvBinding?,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder?.apply {
                        txtView.text = model
                    }

                }
            }

        ){
            override fun getItemCount(): Int {
                return super.getItemCount()
            }

            override fun getItemId(position: Int): Long {
                return super.getItemId(position)
            }
        }

        recyclerView.adapter = adapter

    }

    private fun callCustomdapter(recyclerView: RecyclerView) {
        val list = ArrayList<CustomObject>()
        list.add(CustomObject("Indu", 25))
        list.add(CustomObject("GAvish", 2))
        list.add(CustomObject("Hyan", 67))
        list.add(CustomObject("Rishu", 89))
        list.add(CustomObject("Chanchal", 5))

        list.sortedWith(compareBy<CustomObject>{it.name}.thenBy { it.age })
        list.sortBy { it.age }

        adapterCustom = object : RecyclerViewGenricAdapter<CustomObject,ItemTvBinding>(
            list,
            R.layout.item_tv, object :
                RecyclerCallback<ItemTvBinding, CustomObject> {
                override fun bindData(
                    binder: ItemTvBinding?,
                    model: CustomObject,
                    position: Int,
                    itemView: View
                ) {

                    binder?.apply {
                        txtView.text = model.name
                        txtViewAge.text = model.age.toString()
                    }

                }

                override fun getFilterChar(row: CustomObject, s: String): Boolean {
                    if (row.name.lowercase(Locale.getDefault()).contains(s) || row.age.toString()
                            .contains(s)
                    ) {
                        return true
                    }
                    return false
                }
            }
        ){
            override fun getItemCount(): Int {
                return super.getItemCount()
            }

            override fun getItemId(position: Int): Long {
                return super.getItemId(position)
            }
        }

        recyclerView.adapter = adapterCustom

    }

    data class CustomObject(val name: String, val age: Int)
}