package com.dasha.dogfactsproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dasha.dogfactsproject.data.model.Fact
import com.dasha.dogfactsproject.databinding.RecyclerRowBinding


class FactsAdapter(private val onItemClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<FactsAdapter.FactsHolder>() {

    private var facts : List<String> = emptyList()

    fun setList(newList: List<String>){
        println("got adapter set list")
        facts = newList
        notifyDataSetChanged()
    }

    class FactsHolder(
        private val itemBinding: RecyclerRowBinding,
        private val onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(fact: String) {
        itemBinding.textView.text = fact
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsHolder {
        val itemBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
        return FactsHolder(itemBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: FactsHolder, position: Int) {
       holder.bind(facts[position])
    }

    override fun getItemCount(): Int {
        return facts.size
    }

}