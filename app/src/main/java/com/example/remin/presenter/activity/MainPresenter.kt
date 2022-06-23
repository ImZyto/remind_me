package com.example.remin.presenter.activity

import com.example.remin.view.display.MainDisplay

class MainPresenter(display: MainDisplay) {
    init {
        display.initBottomNavigation()
    }
}