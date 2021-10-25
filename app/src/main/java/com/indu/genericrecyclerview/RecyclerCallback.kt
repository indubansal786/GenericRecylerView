package com.indu.genericrecyclerview

import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * Created by Indu Bala on 24/10/21.
 */
interface RecyclerCallback<VM : ViewDataBinding, T> {
    fun bindData(binder: VM?, model: T, position: Int, itemView: View)
    fun getFilterChar(row: T, s: String): Boolean{
        return false
    }
}
