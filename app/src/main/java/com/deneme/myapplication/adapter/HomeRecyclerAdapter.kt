package com.deneme.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.deneme.myapplication.databinding.RecyclerRowBinding
import com.deneme.myapplication.model.PostData
import com.squareup.picasso.Picasso

class HomeRecyclerAdapter(val postList : ArrayList<PostData>) : RecyclerView.Adapter<HomeRecyclerAdapter.PostHolder>() {

    inner class PostHolder(private val binding : RecyclerRowBinding ) : RecyclerView.ViewHolder(binding.root){

        fun bind( postdata : PostData){
            binding.recyclerRowTextViewUsername.text = postdata.username
            binding.recyclerRowTextViewComment.text = postdata.userCurrent
            Picasso.get().load(postdata.imageUrl).into(binding.recyclerRowImageView)
            //load ile resim yolu belirlendi, into() ile resmin yükleneceği imageView öğesi belirlendi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)

        holder.itemView.setOnClickListener {
            println("jdshfkdhfkdsjfhsdkjf")
        }

    }
}