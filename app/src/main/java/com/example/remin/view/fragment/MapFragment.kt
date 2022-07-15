package com.example.remin.view.fragment

import android.annotation.SuppressLint
import android.location.Address
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

class MapFragment : Fragment(), MapDisplay {

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

        val languages = arrayOf("C", "C++", "Java", "C#", "PHP", "JavaScript", "jQuery", "AJAX", "JSON")

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(context!!, android.R.layout.select_dialog_singlechoice, languages)
        searchBar.threshold = 1
        searchBar.setAdapter(adapter)

        searchBar.setOnTouchListener(OnTouchListener { _, event ->
            val drawableRight = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= searchBar.right - searchBar.compoundDrawables[drawableRight].bounds.width()
                ) {
                    // your action here
                    addressesSearch(searchBar.text)
                }
            }
            false
        })
    }

    fun addressesSearch(vararg params: Any): List<Address?>? {
        val locationAddress = params[0] as String
        //mIndex = params[1] as Int
        val geocoder = GeocoderNominatim(System.getProperty("http.agent"))
        geocoder.setOptions(true) //ask for enclosing polygon (if any)
        //GeocoderGraphHopper geocoder = new GeocoderGraphHopper(Locale.getDefault(), graphHopperApiKey);
        return try {
            val viewbox: BoundingBox = map.boundingBox
            geocoder.getFromLocationName(
                locationAddress, 1,
                viewbox.getLatSouth(), viewbox.getLonEast(),
                viewbox.getLatNorth(), viewbox.getLonWest(), false
            )
        } catch (e: Exception) {
            null
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