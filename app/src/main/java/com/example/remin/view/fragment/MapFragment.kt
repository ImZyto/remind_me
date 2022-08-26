package com.example.remin.view.fragment

import com.example.remin.view.adapter.LocationAdapter
import android.annotation.SuppressLint
import android.location.Address
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.bonuspack.location.GeocoderNominatim
import java.lang.Exception
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.AutoCompleteTextView
import android.widget.AdapterView

import android.widget.AdapterView.OnItemClickListener
import androidx.navigation.Navigation
import com.example.remin.view.utils.GetAddressesTask
import org.osmdroid.views.overlay.Marker


class MapFragment : Fragment(), MapDisplay {

    lateinit var places: ArrayList<Address?>
    lateinit var adapter: LocationAdapter
    lateinit var locationAddress: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
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
        val startPoint = GeoPoint(52.40, 16.90)
        val startMarker = Marker(map)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(startMarker)
        map.controller.setCenter(startingPoint)

        places = arrayListOf()

        adapter = LocationAdapter(context!!, android.R.layout.select_dialog_singlechoice, places)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initSearchBar() {
        val searchBarElt: AutoCompleteTextView = searchBarAcTv
        searchBarElt.threshold = 3
        searchBarElt.setAdapter(adapter)

        searchBarElt.setOnTouchListener(OnTouchListener { _, event ->
            val drawableRight = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= searchBarElt.right - searchBarElt.compoundDrawables[drawableRight].bounds.width()
                ) {
                    // your action here
                    locationAddress = searchBarElt.text.toString()
                    val asyncTask: GetAddressesTask =
                        GetAddressesTask(object : GetAddressesTask.AsyncResponse {
                            override fun processFinish(addresses: List<Address?>?) {
                                adapter.clear()
                                adapter.addAll((addresses ?: ArrayList<Address>()))
                                adapter.filter.filter(locationAddress, null)
                                adapter.notifyDataSetChanged()
                                searchBarElt.showDropDown()
                            }
                        }).execute(locationAddress) as GetAddressesTask
                }
            }
            false
        })

        searchBarElt.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                searchBarElt.setText("")
                Navigation.findNavController(requireView()).navigate(R.id.action_mapFragment_to_createTaskFragment, Bundle().apply { putString("location", adapter.filtered[position]!!.extras["display_name"].toString()) })
            }
        }
    }

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