package com.anfeng.game.ui.widgets.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *  通用适配器,使用
 */
open class UniversalAdapter(private val context: Context, private val map: HashMap<out Any, Int>, var data: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_FOOTER_VIEW = 10000

    var footerView: View? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_FOOTER_VIEW) {
            return ViewHolder(footerView!!)
        }
        val layoutResId: Int? = map.values.elementAt(viewType)
        val view = LayoutInflater.from(context).inflate(layoutResId!!, parent, false)

        val holder = ViewHolder(view)

        view.setOnClickListener {
            onItemClickListener?.invoke(view, data[holder.adapterPosition], holder.adapterPosition)
        }
        view.setOnLongClickListener {
            onItemLongClickListener?.invoke(view, data[holder.adapterPosition], holder.adapterPosition) ?: false
        }

        return holder
    }

    override fun getItemCount(): Int {
        return data.size + if (footerView == null) 0 else 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= data.size)
            return TYPE_FOOTER_VIEW

        val bean = data[position]
        val type: Any
        if (bean is TypeBean) {
            type = bean.type
        } else type = bean.javaClass
        val indexType = map.keys.indexOf(type)
        return indexType
    }

    data class TypeBean(val type: Int, val item: Any)

    override final fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, payloads: MutableList<Any>?) {
        if (payloads?.isEmpty() ?: true) {
            onBindViewHolder(holder, position)
        } else
            if (position < data.size) {
                refresh(holder!!.itemView, data[position], position, payloads!!)
            }
    }

    override final fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position < data.size) {
            refresh(holder!!.itemView, data[position], position)
        }
    }


    var onItemClickListener: ((v: View, item: Any, position: Int) -> Unit)? = null
    var onItemLongClickListener: ((v: View, item: Any, position: Int) -> Boolean)? = null

    open fun refresh(view: View, item: Any, position: Int, payloads: List<Any>) {}
    open fun refresh(view: View, item: Any, position: Int) {}

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}