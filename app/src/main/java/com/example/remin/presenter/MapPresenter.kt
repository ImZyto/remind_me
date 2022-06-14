package com.example.remin.presenter

import com.example.remin.view.display.MapDisplay

class MapPresenter(private val display: MapDisplay) {

    init {
        display.initMap()
    }
}