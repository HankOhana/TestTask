package com.example.tiprankstask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.tiprankstask.R
import com.example.tiprankstask.data.entities.PostAdapterItemEntity
import com.example.tiprankstask.data.entities.PostEntity
import com.example.tiprankstask.databinding.PostItemBinding
import com.example.tiprankstask.databinding.PromotionItemBinding
import com.example.tiprankstask.utils.Constants.Companion.SERVER_DATE_FORMAT
import com.example.tiprankstask.utils.TextUtil
import com.example.tiprankstask.utils.extentions.fromParagraphHTML

class PostsAdapter (private val onClick: (item: PostAdapterItemEntity) -> Unit) :
        PagingDataAdapter<PostAdapterItemEntity, RecyclerView.ViewHolder>(POSTS_DIFF) {

    inner class PostViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(postEntity: PostEntity) {
            binding.apply {
                postTitle.text = postEntity.title
                authorName.text = postEntity.author.name
                postDescription.fromParagraphHTML(postEntity.description)
                postImage.load(postEntity.image.src) {
                    placeholder(R.drawable.post_image_placeholder)
                }
                authorIcon.load(postEntity.author.image.src) {
                    placeholder(R.drawable.ic_launcher_foreground)
                }
                date.text = TextUtil.getFormattedDate(postEntity.date, SERVER_DATE_FORMAT)
                root.setOnClickListener { onClick(PostAdapterItemEntity.PostItem(postEntity)) }
            }
        }
    }

    inner class PromotionViewHolder(private val binding: PromotionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(promoItem: PostAdapterItemEntity.PromotionItem) {
            binding.promotionButton.setOnClickListener { onClick(promoItem) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        item?.let {
            return when(it) {
                is PostAdapterItemEntity.PostItem -> POST_VIEW_TYPE
                is PostAdapterItemEntity.PromotionItem -> PROMOTION_VIEW_TYPE
            }
        }
        throw UnsupportedOperationException("Unknown type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemEntity = getItem(position)
        itemEntity?.let { item ->
            when(item) {
                is PostAdapterItemEntity.PostItem -> {
                    val viewHolder = holder as PostViewHolder
                    viewHolder.bindView(item.post)
                }
                is PostAdapterItemEntity.PromotionItem -> {
                    val viewHolder = holder as PromotionViewHolder
                    viewHolder.bindView(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            POST_VIEW_TYPE -> PostViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            PROMOTION_VIEW_TYPE -> PromotionViewHolder(PromotionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw UnsupportedOperationException("Unknown type")
        }
    }


    companion object {
        val POSTS_DIFF = object : DiffUtil.ItemCallback<PostAdapterItemEntity>() {
            override fun areItemsTheSame(oldItem: PostAdapterItemEntity, newItem: PostAdapterItemEntity) : Boolean {
                return (oldItem is PostAdapterItemEntity.PostItem && newItem is PostAdapterItemEntity.PostItem &&
                        oldItem.post.id == newItem.post.id) ||
                        (oldItem is PostAdapterItemEntity.PromotionItem && newItem is PostAdapterItemEntity.PromotionItem &&
                                oldItem.link == newItem.link)
            }

            override fun areContentsTheSame(oldItem: PostAdapterItemEntity, newItem: PostAdapterItemEntity) =
                oldItem == newItem
        }

        const val POST_VIEW_TYPE = R.layout.post_item
        const val PROMOTION_VIEW_TYPE = R.layout.promotion_item
    }
}