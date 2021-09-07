package com.kharozim.consumerapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.kharozim.consumerapp.R
import com.kharozim.consumerapp.databinding.ItemUserBinding
import com.kharozim.consumerapp.model.UserModel

class HomeAdapter(
    private val items: List<UserModel>,
) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    interface MyListener {
        fun onClicked(position: Int, data: Any?)
    }

    private var itemClick: MyListener? = null
    fun onClickItem(listener: MyListener) {
        itemClick = listener
    }


    inner class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: UserModel, position: Int) {
            binding.run {
                ivUser.load(item.avatarUrl) {
                    crossfade(true)
                    placeholder(R.mipmap.ic_launcher_round)
                    transformations(CircleCropTransformation())
                }

                tvName.text = item.name
                tvUsername.text = item.login

                root.setOnClickListener {
                    itemClick?.onClicked(position, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemUserBinding.inflate(
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