package com.example.githubuser2.ui.views.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubuser2.R
import com.example.githubuser2.databinding.ItemUserFollowBinding
import com.example.githubuser2.repository.local.UserEntity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    var items = listOf<UserEntity>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface ItemClick {
        fun onItemClick(position: Int, data: Any?)
    }

    var onClick: ItemClick? = null
    fun clickItem(listener: ItemClick) {
        onClick = listener
    }

    inner class MyViewHolder(private val binding: ItemUserFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: UserEntity, position: Int) {
            binding.run {

                if (item.avatarUrl.isNotEmpty()) {
                    itemIvUser.load(item.avatarUrl) {
                        crossfade(true)
                        placeholder(R.mipmap.ic_launcher_round)
                    }
                }
                itemTvName.text = item.name

                root.setOnClickListener {
                    onClick?.onItemClick(position, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemUserFollowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}