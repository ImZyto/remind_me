package com.example.remin.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remin.BuildConfig
import com.example.remin.R
import com.example.remin.model.Constants.EXTRA_TASK_LOCALIZATION
import com.example.remin.model.dataclass.Task
import com.example.remin.presenter.MapPresenter
import com.example.remin.view.adapter.LocationAdapter
import com.example.remin.view.adapter.MapTaskListAdapter
import com.example.remin.view.display.MapDisplay
import com.example.remin.view.utils.GetAddressesFromGeoPointTask
import com.example.remin.view.utils.GetAddressesFromLocationNameTask
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.Marker.OnMarkerClickListener





class MapFragment : Fragment(), MapDisplay {

    lateinit var places: ArrayList<Address?>
    lateinit var locationAdapter: LocationAdapter
    lateinit var locationAddress: String
    lateinit var searchBarElt: AutoCompleteTextView
    lateinit var currentMarker: Marker
    lateinit var taskListAdapter: MapTaskListAdapter

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
        var userLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        userLocationOverlay.enableMyLocation()
        val locationManager = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        currentMarker = Marker(map)

        var startingPoint: GeoPoint? = null
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                if (location != null && startingPoint == null) {
                    startingPoint = GeoPoint(location.latitude, location.longitude)
                    map.controller.setCenter(startingPoint)
                }
            }
            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000,
                100f,
                locationListener
            )
        }

        places = arrayListOf()

        locationAdapter = LocationAdapter(context!!, android.R.layout.select_dialog_singlechoice, places)
    }

    override fun addClickListener() {
        val mReceive: MapEventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(geoPoint: GeoPoint): Boolean {
                Toast.makeText(
                    context!!,
                    geoPoint.latitude.toString() + " - " + geoPoint.longitude,
                    Toast.LENGTH_LONG
                ).show()
                val currentPoint = GeoPoint(geoPoint.latitude, geoPoint.longitude)
                currentMarker.position = currentPoint
                currentMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                map.overlays.add(currentMarker)
                map.invalidate()
                val asyncTask: GetAddressesFromGeoPointTask =
                    GetAddressesFromGeoPointTask(object : GetAddressesFromGeoPointTask.AsyncResponse {
                        override fun processFinish(addresses: List<Address?>?) {
                            addresses?.let {
                                if (addresses.isNotEmpty()) {
                                    searchBarElt.setText(addresses[0]?.locality + addresses[0]?.getAddressLine(0))
                                    searchBarElt.requestFocus()
                                    locationAdapter.clear()
                                    locationAdapter.addAll((addresses ?: ArrayList<Address>()))
                                    locationAdapter.filter.filter(addresses[0]?.locality, null)
                                    locationAdapter.notifyDataSetChanged()
                                    searchBarElt.showDropDown()
                                }
                            }
                        }
                    }).execute(geoPoint) as GetAddressesFromGeoPointTask
                return false
            }

            override fun longPressHelper(p: GeoPoint): Boolean {
                return false
            }
        }
        map.overlays.add(MapEventsOverlay(mReceive))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initSearchBar() {
        searchBarElt = searchBarAcTv
        searchBarElt.threshold = 3
        searchBarElt.setAdapter(locationAdapter)

        searchBarElt.setOnTouchListener(OnTouchListener { _, event ->
            val drawableRight = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= searchBarElt.right - searchBarElt.compoundDrawables[drawableRight].bounds.width()
                ) {
                    // your action here
                    locationAddress = searchBarElt.text.toString()
                    val asyncTask: GetAddressesFromLocationNameTask =
                        GetAddressesFromLocationNameTask(object : GetAddressesFromLocationNameTask.AsyncResponse {
                            override fun processFinish(addresses: List<Address?>?) {
                                locationAdapter.clear()
                                locationAdapter.addAll((addresses ?: ArrayList<Address>()))
                                locationAdapter.filter.filter(locationAddress, null)
                                locationAdapter.notifyDataSetChanged()
                                searchBarElt.showDropDown()
                            }
                        }).execute(locationAddress) as GetAddressesFromLocationNameTask
                }
            }
            false
        })

        searchBarElt.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                searchBarElt.setText("")
                navigateToCreateTaskFragment(requireView()).navigate(R.id.action_mapFragment_to_createTaskFragment, Bundle().apply { putString("location", locationAdapter.filtered[position]!!.extras["display_name"].toString()) })
            }
        }
    }

    override fun loadTaskList(taskList: List<Task>) {
        if (taskList.isNotEmpty()) {
            taskListHorizontalRv.visibility = View.VISIBLE
            taskListHorizontalRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            taskListHorizontalRv.adapter = MapTaskListAdapter(requireContext(), taskList) {
                task -> run {
                    val taskGeoPoint = GeoPoint(task.latitude, task.longitude)
                    map.controller.setCenter(taskGeoPoint)
                }
            }
            taskListAdapter = taskListHorizontalRv.adapter as MapTaskListAdapter
            var firstGeoPoint: GeoPoint? = null
            taskList.forEach {task -> run {
                val taskGeoPoint = GeoPoint(task.latitude, task.longitude)
                val taskMarker = Marker(map)
                taskMarker.position = taskGeoPoint
                taskMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                taskMarker.setOnMarkerClickListener { marker, mapView ->
                    markerClickHandler(marker)
                    true
                }
                map.overlays.add(taskMarker)
                if (firstGeoPoint == null) {
                    firstGeoPoint = taskGeoPoint
                    map.controller.setCenter(firstGeoPoint)
                }
            }}
        }
    }

    private fun markerClickHandler(marker: Marker) {
        val selectedTask = taskListAdapter.getTaskList().find { task -> task.latitude == marker.position.latitude && task.longitude == marker.position.longitude}
        if (selectedTask != null) {
            taskListHorizontalRv.scrollToPosition(taskListAdapter.getTaskPosition(selectedTask))
        }
    }

    override fun navigateToCreateTaskFragment(localization: String) {
        val bundle = Bundle().apply { putString(EXTRA_TASK_LOCALIZATION, localization) }
        Navigation.findNavController(requireView()).navigate(R.id.action_mapFragment_to_createTaskFragment, bundle)
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