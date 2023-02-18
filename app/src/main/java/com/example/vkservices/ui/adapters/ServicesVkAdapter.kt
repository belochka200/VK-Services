package com.example.vkservices.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.vkservices.databinding.ItemServiceCardBinding
import com.example.vkservices.ui.models.Service

class ServicesVkAdapter(private val servicesDataset: List<Service>) :
    RecyclerView.Adapter<ServicesVkAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemServiceCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemServiceCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = servicesDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(servicesDataset[position]) {
                binding.apply {
                    serviceCardTitle.text = name
                    serviceCardImage.load(iconUrl) {
                        crossfade(500)
                    }
                }
            }
        }
    }
}