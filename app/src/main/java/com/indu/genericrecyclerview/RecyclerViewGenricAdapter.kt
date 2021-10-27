package com.indu.genericrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class RecyclerViewGenricAdapter<T, VM : ViewDataBinding>(
    private var items: ArrayList<T>,
    private val layoutId: Int,
    val bindingInterface: RecyclerCallback<VM, T>
) :
    RecyclerView.Adapter<RecyclerViewGenricAdapter<T, VM>.RecyclerViewHolder11>(),
    Filterable {
    private val totalItems: ArrayList<T> = items

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                items = if (charString.isEmpty()) {
                    totalItems
                } else {
                    val filteredList = ArrayList<T>()
                    for (row in totalItems) {

                        if (bindingInterface.getFilterChar(
                                row,
                                charString.lowercase(Locale.getDefault())
                            )
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = items
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                items = filterResults.values as ArrayList<T>
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder11 {
        val v = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
        return RecyclerViewHolder11(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder11, position: Int) {
        val item = items[position]
        holder.itemView.setTag(position)
        holder.bindData(item, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }



//    fun updateAdpater(items: ArrayList<T>) {
//        this.items = items
//        notifyDataSetChanged()
//    }


    inner class RecyclerViewHolder11(view: View?) : RecyclerView.ViewHolder(
        view!!
    ) {
        var binding: VM?
        fun bindData(model: T, pos: Int) {
            bindingInterface.bindData(binding, model, pos, itemView)
        }

        init {
            binding = DataBindingUtil.bind(view!!)
        }
    }


}


