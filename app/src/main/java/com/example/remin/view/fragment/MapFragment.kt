package com.example.remin.view.fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.remin.BuildConfig
import com.example.remin.R
import com.example.remin.presenter.MapPresenter
import com.example.remin.view.display.MapDisplay
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.compass.CompassOverlay


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

    override fun initMap() {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(16.5)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        map.setMultiTouchControls(false)

        var compassOverlay: CompassOverlay = CompassOverlay(activity!!.applicationContext, map)
        map.overlays.add(compassOverlay)

        var startingPoint: GeoPoint = GeoPoint(52.40, 16.90)
        map.controller.setCenter(startingPoint)
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