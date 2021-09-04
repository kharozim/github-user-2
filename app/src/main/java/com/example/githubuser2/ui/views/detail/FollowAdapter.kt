package com.example.githubuser2.ui.views.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubuser2.R
import com.example.githubuser2.databinding.ItemUserFollowBinding
import com.example.githubuser2.model.responses.UserItem

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.MyViewHolder>() {

    var items = listOf<UserItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun resetData() {
        items = emptyList()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: ItemUserFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: UserItem) {
            binding.run {
                itemTvName.text = root.context.getString(R.string.tag_username,item.login)
                itemIvUser.load(item.avatarUrl) {
                    crossfade(true)
                    placeholder(R.mipmap.ic_launcher_round)
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
        holder.bindData(items[position])
    }

    override fun getItemCount(): Int = items.size
}