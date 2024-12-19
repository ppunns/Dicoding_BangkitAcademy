package com.dicoding.picodiploma.loginwithanimation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemLayoutBinding

class StoryAdapter(
    private val listStory: List<ListStoryItem>,
    private val onItemClick: (ListStoryItem) -> Unit
) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem, onItemClick: (ListStoryItem) -> Unit) {
            binding.apply {
                tvName.text = story.name
                tvDescription.text = story.description
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(ivStory)
                
                // Tambahkan onClick di root view
                root.setOnClickListener {
                    onItemClick(story)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listStory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position], onItemClick)
    }
}