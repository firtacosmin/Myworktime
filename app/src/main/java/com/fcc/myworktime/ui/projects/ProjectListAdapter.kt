package com.fcc.myworktime.ui.projects

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcc.myworktime.R
import com.fcc.myworktime.databinding.ProjectsListItemBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject



/**
 * Created by firta on 8/31/2017.
 * The adapter for the [RecyclerView] that will contain the projects
 */
class ProjectListAdapter: RecyclerView.Adapter<ProjectListAdapter.ProjectListItemViewHolder>() {

    var itemList:MutableList<String> = ArrayList()
    val itemClickedEvent = PublishSubject.create<Int>()



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
        RxView.clicks(holder!!.itemView).subscribe({itemClickedEvent.onNext(position)})

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProjectListItemViewHolder {
        val binding = DataBindingUtil.inflate<ProjectsListItemBinding>(LayoutInflater.from(parent?.context), R.layout.projects_list_item, parent, false)
        return ProjectListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    /**
     * The [RecyclerView.ViewHolder] for the list of projects
     */
    class ProjectListItemViewHolder(
            var binding: ProjectsListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun setText(text:String){
            binding.infoText.text = text
        }
    }
}