package com.example.navigationgraph.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationgraph.databinding.ItemCountryOverviewBinding
import com.example.navigationgraph.models.Country

class CountryAdapter : ListAdapter<Country, CountryItem>(object: DiffUtil.ItemCallback<Country>(){

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
        oldItem == newItem

}){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItem {
        return CountryItem(
            ItemCountryOverviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CountryItem, position: Int) {
        holder.bind(getItem(position))
    }

}

class CountryItem(
    private val binding: ItemCountryOverviewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(countryData: Country) {
        binding.tvCountryCapital.text = countryData.capital
        binding.tvCountryName.text = countryData.name
    }
}