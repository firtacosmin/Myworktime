package com.fcc.myworktime.ui.utils

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.ListItemProjectsBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject



/**
 * Created by firta on 8/31/2017.
 * The adapter for the [RecyclerView] that will contain the projects
 */
class TextListAdapter : RecyclerView.Adapter<TextListAdapter.ProjectListItemViewHolder>() {

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

    override fun onBindViewHolder(holder: ProjectListItemViewHolder?, position: Int) {

        holder?.setText(itemList[position])
        RxView.clicks(holder!!.itemView).subscribe{itemClickedEvent.onNext(position)}
        RxView.clicks(holder.binding.imgDelete).subscribe{deleteClickedEvent.onNext(position)}
        RxView.clicks(holder.binding.imgEdit).subscribe{editClickedEvent.onNext(position)}


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProjectListItemViewHolder {
        val binding = DataBindingUtil.inflate<ListItemProjectsBinding>(LayoutInflater.from(parent?.context), R.layout.list_item_projects, parent, false)
        return ProjectListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    /**
     * The [RecyclerView.ViewHolder] for the list of projects
     */
    class ProjectListItemViewHolder(
            var binding: ListItemProjectsBinding
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