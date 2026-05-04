package com.example.advanceenglishdictionary.extensions

import android.content.Context
import android.widget.Toast

// Store the last shown Toast globally in this object
private var currentToast: Toast? = null

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    // Cancel previous toast if it exists
    currentToast?.cancel()

    // Create and show new toast
    currentToast = Toast.makeText(this, message, duration)
    currentToast?.show()
}