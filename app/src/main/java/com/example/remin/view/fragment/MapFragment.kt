package com.example.remin.view.fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.remin.R
import com.example.remin.presenter.MapPresenter
import com.example.remin.view.display.MapDisplay
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory


class MapFragment : Fragment(), MapDisplay {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.setTileSource(TileSourceFactory.MAPNIK)
        MapPresenter(this)
    }

    override fun getFragmentContext() = requireContext()



    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

}