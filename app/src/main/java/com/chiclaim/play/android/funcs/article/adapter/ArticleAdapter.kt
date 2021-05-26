package com.chiclaim.play.android.funcs.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chiclaim.play.android.bean.vo.ArticleVO
import com.chiclaim.play.android.databinding.ItemArticleBinding


/**
 *
 * @author by chiclaim@google.com
 */
class ArticleAdapter : ListAdapter<ArticleVO, ArticleAdapter.ArticleViewHolder>(Diff) {


    class ArticleViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)


    companion object Diff : DiffUtil.ItemCallback<ArticleVO>() {
        override fun areItemsTheSame(oldItem: ArticleVO, newItem: ArticleVO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArticleVO, newItem: ArticleVO): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.article = getItem(position)
        holder.binding.executePendingBindings()
    }

}