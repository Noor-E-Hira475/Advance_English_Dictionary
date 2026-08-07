package com.example.advanceenglishdictionary.extensions

import android.text.Spanned
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Extension on String to format definition text such that the leading confused word / first word is bolded.
 */
fun String.toBoldFirstWordHtml(pairWord: String? = null): Spanned {
    val boldHtml = when {
        !pairWord.isNullOrEmpty() && this.startsWith(pairWord, ignoreCase = true) -> {
            "<b>${this.substring(0, pairWord.length)}</b>${this.substring(pairWord.length)}"
        }
        else -> {
            val spaceIndex = this.indexOf(' ')
            if (spaceIndex != -1) {
                "<b>${this.substring(0, spaceIndex)}</b>${this.substring(spaceIndex)}"
            } else {
                "<b>$this</b>"
            }
        }
    }
    return HtmlCompat.fromHtml(boldHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

/**
 * Extension on TextView to directly bind definition text with the first/confused word bolded.
 */
fun TextView.setBoldDefinition(definition: String, pairWord: String? = null) {
    this.text = definition.toBoldFirstWordHtml(pairWord)
}

/**
 * Extension on LifecycleOwner to simplify collecting StateFlow on Lifecycle STARTED state.
 */
fun <T> LifecycleOwner.collectState(flow: StateFlow<T>, action: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { action(it) }
        }
    }
}
