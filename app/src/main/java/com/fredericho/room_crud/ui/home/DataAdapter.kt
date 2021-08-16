package com.fredericho.room_crud.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fredericho.room_crud.R
import com.fredericho.room_crud.data.entity.Mahasiswa
import com.fredericho.room_crud.databinding.ListDataBinding
import com.fredericho.room_crud.ui.detail.DetailActivity

class DataAdapter(
    private val listData : MutableList<Mahasiswa>)
    : RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder>() {

    inner class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ListDataBinding.bind(itemView)

        fun bind(mahasiswa : Mahasiswa){
            with(binding){
                tvName.text = mahasiswa.name
                tvNim.text = mahasiswa.nim
                Glide.with(itemView.context)
                    .load(mahasiswa.image)
                    .circleCrop()
                    .into(ivImage)
            }
            itemView.setOnClickListener {
                val detail = Intent(itemView.context, DetailActivity::class.java)
                    .putExtra(DetailActivity.EXTRA_DATA, mahasiswa.id)
                itemView.context.startActivity(detail)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        return DataAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_data, parent, false))
    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}