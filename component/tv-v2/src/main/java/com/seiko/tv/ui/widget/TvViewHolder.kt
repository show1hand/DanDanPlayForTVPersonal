package com.seiko.tv.ui.widget

import android.view.KeyEvent

interface TvViewHolder {

    fun onCreated()

    fun onBound()

    fun onDetach()

    fun onRecycled()

    fun onFocused()

    fun onUnFocused()

    fun hasFocus(): Boolean

    fun dispatchKeyEvent(event: KeyEvent): Boolean

}