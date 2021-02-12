package com.example.tiprankstask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tiprankstask.databinding.LoadStateProgressBinding

class PostsLoadStateAdapter : LoadStateAdapter<PostsLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(private val binding: LoadStateProgressBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(loadState: LoadState) {
            binding.progress.isVisible = loadState is LoadState.Loading
                    && absoluteAdapterPosition != 0
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bindView(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(LoadStateProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}