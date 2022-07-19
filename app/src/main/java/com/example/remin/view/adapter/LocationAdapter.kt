package com.example.remin.view.adapter
import android.content.Context
import android.location.Address
import android.widget.TextView

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View

import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.remin.R


class LocationAdapter(context: Context, var resources: Int, var items: List<Address?>) :
    ArrayAdapter<Address>(context, resources, items) {

    var filtered = ArrayList<Address>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.location_search_item, parent, false)
        val textView: TextView = view.findViewById(R.id.locationName)
        var address: Address = filtered[position]
        textView.text = address.locality
        return view
    }

    override fun getCount(): Int = filtered.size

    override fun getItem(position: Int): Address = filtered[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getFilter() = filter

    private var filter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = FilterResults()

            val query = if (constraint != null && constraint.isNotEmpty()) autocomplete(constraint.toString())
            else arrayListOf()

            results.values = query
            results.count = query.size

            return results
        }

        private fun autocomplete(input: String): ArrayList<Address> {
            val results = arrayListOf<Address>()

            for (address in items) {
                if (address!!.locality.lowercase().contains(input.lowercase())) results.add(address)
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
            filtered = results.values as ArrayList<Address>
            notifyDataSetInvalidated()
        }

        override fun convertResultToString(result: Any) = (result as Address).locality
    }
}