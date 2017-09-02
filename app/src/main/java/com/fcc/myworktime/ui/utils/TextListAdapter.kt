package com.fcc.myworktime.ui.utils

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.ListItemSimpleViewBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject


/**
 * Created by firta on 8/31/2017.
 * The adapter for the [RecyclerView] that will contain the projects
 */
class TextListAdapter : RecyclerView.Adapter<TextListAdapter.ListItemSimpleViewHolder>() {

    private var itemList:MutableList<String> = ArrayList()
    val itemClickedEvent = PublishSubject.create<Int>()!!
    val deleteClickedEvent = PublishSubject.create<Int>()!!
    val editClickedEvent = PublishSubject.create<Int>()!!




    fun setItems(items:List<String>){
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item:String){
        if (itemList.add(item)) {
            notifyItemInserted(itemList.indexOf(item))
        }
    }

    override fun onBindViewHolder(holderSimple: ListItemSimpleViewHolder?, position: Int) {

        holderSimple?.setText(itemList[holderSimple.adapterPosition])
        RxView.clicks(holderSimple!!.itemView).subscribe{itemClickedEvent.onNext(holderSimple.adapterPosition)}
        RxView.clicks(holderSimple.binding.imgDelete).subscribe{deleteClickedEvent.onNext(holderSimple.adapterPosition)}
        RxView.clicks(holderSimple.binding.imgEdit).subscribe{editClickedEvent.onNext(holderSimple.adapterPosition)}


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemSimpleViewHolder {
        val binding = DataBindingUtil.inflate<ListItemSimpleViewBinding>(LayoutInflater.from(parent?.context), R.layout.list_item_simple_view, parent, false)
        return ListItemSimpleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    /**
     * The [RecyclerView.ViewHolder] for the list of projects
     */
    class ListItemSimpleViewHolder(
            var binding: ListItemSimpleViewBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun setText(text:String){
            binding.infoText.text = text
        }
    }

    fun updateLastItem(newItem: String) {
        itemList[itemCount - 1] = newItem
        notifyItemChanged(itemCount - 1)
    }

    fun updateFirstItem(newItem:String){
        itemList[0] = newItem
        notifyItemChanged(0)
    }
    fun addItemInFront(newItem:String){
        itemList.add(0, newItem)
        notifyItemInserted(0)
    }

    fun removeItem(position: Int) {

        itemList.removeAt(position)
        notifyItemRemoved(position)

    }
}