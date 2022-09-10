package com.example.remin.view.utils

import android.location.Address
import android.os.AsyncTask
import org.osmdroid.bonuspack.location.GeocoderNominatim
import java.lang.Exception

class GetAddressesFromLocationNameTask(val delegate: AsyncResponse) : AsyncTask<String, Void, List<Address?>?>() {
    interface AsyncResponse {
        fun processFinish(output: List<Address?>?)
    }

    override fun doInBackground(vararg location: String?): List<Address?>? {
        val geocoder = GeocoderNominatim(System.getProperty("http.agent"))
        geocoder.setOptions(true)
        return try {
            geocoder.getFromLocationName(
                location[0], 3
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(result: List<Address?>?) {
        delegate.processFinish(result)
    }
}