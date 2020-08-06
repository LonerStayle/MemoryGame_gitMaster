package com.example.i4_memorygame_gitversion.view.activity

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    companion object {
       var displayWidth by Delegates.notNull<Int>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        displayWidth = size.x

    }
}
