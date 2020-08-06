package com.example.i4_memorygame_gitversion.bindingadapter

import android.widget.NumberPicker
import androidx.databinding.BindingAdapter

@BindingAdapter("minValue")
fun minValue(view:NumberPicker,min:Int) {view.minValue = min}


@BindingAdapter("maxValue")
fun maxValue(view:NumberPicker,max:Int) {view.maxValue = max}