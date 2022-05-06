package org.wit.zarn.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.zarn.R
import org.wit.zarn.databinding.CardZarnBinding
import org.wit.zarn.models.ZarnModel


interface ZarnClickListener {
    fun onZarnClick(zarn: ZarnModel)
}

class ZarnAdapter constructor(private var zarns: ArrayList<ZarnModel>,
                              private val listener: ZarnClickListener)
    : RecyclerView.Adapter<ZarnAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardZarnBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val zarn = zarns[holder.adapterPosition]
        holder.bind(zarn,listener)
    }

    fun removeAt(position: Int) {
        zarns.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = zarns.size

    inner class MainHolder(val binding : CardZarnBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(zarn: ZarnModel, listener: ZarnClickListener) {
            binding.root.tag = zarn._id
        binding.zarn = zarn
        binding.imageIcon.setImageResource(R.mipmap.client_icon)
        binding.root.setOnClickListener { listener.onZarnClick(zarn) }
        binding.executePendingBindings()
    }
}
}
