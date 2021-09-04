package com.example.githubuser2.ui.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubuser2.R
import com.example.githubuser2.databinding.ItemUserSearchBinding
import com.example.githubuser2.model.responses.UserItem

class UserAdapter(
    private val items: List<UserItem>,
) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    interface MyListener {
        fun onClicked(position: Int, data: Any?)
    }

    private var itemClick: MyListener? = null
    fun onClickItem(listener: MyListener) {
        itemClick = listener
    }


    inner class MyViewHolder(private val binding: ItemUserSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: UserItem, position: Int) {
            binding.run {
                itemTvName.text = item.login
                itemIvUser.load(item.avatarUrl) {
                    crossfade(true)
                    placeholder(R.mipmap.ic_launcher_round)
                }

                root.setOnClickListener {
                    itemClick?.onClicked(position, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemUserSearchBinding.inflate(
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