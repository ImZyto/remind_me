package com.example.remin.view.utils

import android.location.Address
import android.os.AsyncTask
import org.osmdroid.bonuspack.location.GeocoderNominatim
import org.osmdroid.util.GeoPoint
import java.lang.Exception

class GetAddressesFromGeoPointTask(val delegate: AsyncResponse) : AsyncTask<GeoPoint, Void, List<Address?>?>() {
    interface AsyncResponse {
        fun processFinish(output: List<Address?>?)
    }

    override fun doInBackground(vararg geoPoint: GeoPoint?): List<Address?>? {
        val geocoder = GeocoderNominatim(System.getProperty("http.agent"))
        geocoder.setOptions(true)
        return try {
            geocoder.getFromLocation(
                geoPoint[0]!!.latitude, geoPoint[0]!!.longitude, 3
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(result: List<Address?>?) {
        delegate.processFinish(result)
    }
}