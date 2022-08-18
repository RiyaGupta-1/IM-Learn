package com.example.imlearn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter(val listener: listBtnClicked, val context: ContactFragment, val users: List<User>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem: User = users[position]
        //holder.titleImage.setImageURI(currItem.image)
        Glide.with(context).load(currItem.image).into(holder.titleImage)
        holder.details.text= currItem.firstName
        holder.email.text= currItem.email
        holder.phone.text= currItem.phone
        holder.call.setOnClickListener { listener.onCallButtonClicked(currItem) }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        val titleImage: ImageView= itemView.findViewById(R.id.title_image)
        val details: TextView= itemView.findViewById(R.id.details)
        val email: TextView= itemView.findViewById(R.id.email)
        val phone: TextView= itemView.findViewById(R.id.phone)
        val call:Button= itemView.findViewById(R.id.list_btn)

    }

    interface listBtnClicked{
       fun onCallButtonClicked(user:User)
    }
}