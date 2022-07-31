package com.example.remin.view.adapter
import android.content.Context
import android.location.Address
import android.widget.TextView

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View

import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.example.remin.R
import kotlinx.android.synthetic.main.fragment_map.*
import android.widget.AutoCompleteTextView


class LocationAdapter(context: Context, var resources: Int, var items: List<Address?>) :
    ArrayAdapter<Address>(context, resources, items), Filterable {

    var filtered: List<Address?> = items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        /*val seachBar = getView().findViewById<View>(
            R.id.autocomplete_klantnaam
        ) as AutoCompleteTextView*/
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.location_search_item, parent, false)
        val textView: TextView = view.findViewById(R.id.locationName)
        var address: Address? = filtered[position]
        textView.text = address!!.extras["display_name"].toString()
        /*view.setOnClickListener { view ->
            selectedCustomer = view.tag as Customer
            searchBar.startSearch(selectedCustomer)
        }*/
        return view
    }

    override fun getCount(): Int = filtered.size

    override fun getItem(position: Int): Address? = filtered[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                filtered = filterResults.values as ArrayList<Address>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    items
                else
                    items.filter {
                        it!!.extras["display_name"].toString().toLowerCase().contains(queryString.toLowerCase())
                    }
                return filterResults
            }
        }
    }

}