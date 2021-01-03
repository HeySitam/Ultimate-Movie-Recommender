package com.sitamrock11.ultimate_movie_recommender

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ChildItemAdapter(var childItemList:List<ChildItem>?):RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder>() {
    class ChildViewHolder(var itemView: View):RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById<TextView>(R.id.tvTitle);
        var tvYear: TextView = itemView.findViewById<TextView>(R.id.tvYear);
        var tvType: TextView = itemView.findViewById<TextView>(R.id.tvType);
        var tvPoster: ImageView = itemView.findViewById<ImageView>(R.id.imgPoster);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildItemAdapter.ChildViewHolder {
      var view:View=LayoutInflater.from(parent.context).inflate(R.layout.child_item,parent,false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildItemAdapter.ChildViewHolder, position: Int) {
       var childItem:ChildItem= childItemList!![position]
        holder.tvTitle.text=childItem.title
        holder.tvYear.text=childItem.year
        holder.tvType.text=childItem.type
        Picasso.get().load(childItem.poster).into(holder.tvPoster)

    }

    override fun getItemCount(): Int {
        return childItemList!!.size
    }
}