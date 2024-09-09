package com.example.jollycat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jollycat.databinding.DeviceCardBinding

class DevAdapter(private val context: Context, private var listCats: List<Cats>) : RecyclerView.Adapter<DevAdapter.MainViewHolder>() {

    inner class MainViewHolder(private val binding: DeviceCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cats) {
            Glide.with(context)
                .load(cat.catImage)
                .into(binding.catImageIV)
            binding.catNameTV.text = cat.catName
            binding.catDescTV.text = cat.catDescription
            binding.catPriceTV.text = cat.catPrice.toString()

            itemView.setOnClickListener {
                val intent = Intent(context, ActivityCatsDetailPage::class.java)
                intent.putExtra("CatID", cat.catID)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = DeviceCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCats.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val cat = listCats[position]
        holder.bind(cat)
    }

    fun updateList(newList: List<Cats>) {
        listCats = newList
        notifyDataSetChanged()
    }
}
