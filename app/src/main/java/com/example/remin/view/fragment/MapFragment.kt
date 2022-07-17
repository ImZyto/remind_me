package com.example.remin.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remin.BuildConfig
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import com.example.remin.presenter.MapPresenter
import com.example.remin.view.adapter.MapTaskListAdapter
import com.example.remin.view.display.MapDisplay
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.bonuspack.location.GeocoderNominatim
import java.lang.Exception
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.AutoCompleteTextView

import android.widget.ArrayAdapter
import java.lang.ref.WeakReference
import java.net.URL
import android.widget.GridView

import android.app.Activity
import android.os.Parcel
import android.os.Parcelable


class MapFragment : Fragment(), MapDisplay {

    lateinit var places: Array<String>
    lateinit var adapter: ArrayAdapter<String>
    lateinit var locationAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapPresenter(this)
    }

    override fun getFragmentContext() = requireContext()

    @SuppressLint("ClickableViewAccessibility")
    override fun initMap() {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(16.5)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        map.setMultiTouchControls(false)

        val compassOverlay = CompassOverlay(requireContext(), map)
        map.overlays.add(compassOverlay)

        val startingPoint = GeoPoint(52.40, 16.90)
        map.controller.setCenter(startingPoint)

        places = arrayOf()

        adapter =
            ArrayAdapter<String>(context!!, android.R.layout.select_dialog_singlechoice, places)
        searchBar.threshold = 1
        searchBar.setAdapter(adapter)

        searchBar.setOnTouchListener(OnTouchListener { _, event ->
            val drawableRight = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= searchBar.right - searchBar.compoundDrawables[drawableRight].bounds.width()
                ) {
                    // your action here
                    locationAddress = searchBar.text.toString()
                    val asyncTask: GetAddressesTask =
                        GetAddressesTask(object : GetAddressesTask.AsyncResponse {
                            override fun processFinish(addresses: List<Address?>?) {
                                places = (addresses?: ArrayList<Address>()).map { address -> address?.getAddressLine(0)!! }.toTypedArray()
                                adapter =
                                    ArrayAdapter<String>(context!!, android.R.layout.select_dialog_singlechoice, places)
                                searchBar.threshold = 1
                                searchBar.setAdapter(adapter)
                            }
                    }).execute(locationAddress) as GetAddressesTask
                    //val addresses: List<Address?>? = addressesSearch(searchBar.text)
                    //places = (addresses?: ArrayList<Address>()).map { address -> address?.getAddressLine(0)!! }.toTypedArray()
                }
            }
            false
        })
    }

    private class GetAddressesTask(val delegate: AsyncResponse) : AsyncTask<String, Void, List<Address?>?>() {
        interface AsyncResponse {
            fun processFinish(output: List<Address?>?)
        }

        //var delegate: AsyncResponse? = null

        override fun doInBackground(vararg location: String?): List<Address?>? {
            //mIndex = params[1] as Int
            val geocoder = GeocoderNominatim(System.getProperty("http.agent"))
            geocoder.setOptions(true) //ask for enclosing polygon (if any)
            //GeocoderGraphHopper geocoder = new GeocoderGraphHopper(Locale.getDefault(), graphHopperApiKey);
            return try {
                //val viewbox: BoundingBox = map.boundingBox
                geocoder.getFromLocationName(
                    location[0], 1
                )
            } catch (e: Exception) {
                null
            }
        }

        override fun onPostExecute(result: List<Address?>?) {
            delegate.processFinish(result)
        }
    }

    /*fun addressesSearch(vararg params: Any): List<Address?>? {
        val locationAddress = params[0].toString()
        //mIndex = params[1] as Int
        val geocoder = GeocoderNominatim(System.getProperty("http.agent"))
        geocoder.setOptions(true) //ask for enclosing polygon (if any)
        //GeocoderGraphHopper geocoder = new GeocoderGraphHopper(Locale.getDefault(), graphHopperApiKey);
        return try {
            //val viewbox: BoundingBox = map.boundingBox
            geocoder.getFromLocationName(
                locationAddress, 1
            )
        } catch (e: Exception) {
            null
        }
    }*/

    override fun loadTaskList(taskList: List<Task>) {
        taskListHorizontalRv.layoutManager =
        LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        taskListHorizontalRv.adapter = MapTaskListAdapter(requireContext(), taskList){}
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}