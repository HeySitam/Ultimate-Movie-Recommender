package com.sitamrock11.ultimate_movie_recommender

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ParentAdapter(var parentItemList:List<ParentItem>):RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {
    val viewPool:RecyclerView.RecycledViewPool=RecyclerView.RecycledViewPool()
    class ParentViewHolder(var itemView: View):RecyclerView.ViewHolder(itemView) {
        val tvChildHeading: TextView =itemView.findViewById<TextView>(R.id.tvChildHeading)
        val rvChild: RecyclerView=itemView.findViewById(R.id.rvChild)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentAdapter.ParentViewHolder {
        var view:View=LayoutInflater.from(parent.context).inflate(R.layout.parent_item,parent,false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentAdapter.ParentViewHolder, position: Int) {
       var parentItem:ParentItem=parentItemList[position]
        val layoutManager=LinearLayoutManager(holder.rvChild.context,LinearLayoutManager.HORIZONTAL,false)
        layoutManager.initialPrefetchItemCount=parentItem.childItemList!!.size
        val childAdapter=ChildItemAdapter(parentItem.childItemList)
        holder.rvChild.layoutManager=layoutManager
        holder.rvChild.adapter=childAdapter
        holder.rvChild.setRecycledViewPool(viewPool)
    }

    override fun getItemCount(): Int {
        return parentItemList.size
    }
}