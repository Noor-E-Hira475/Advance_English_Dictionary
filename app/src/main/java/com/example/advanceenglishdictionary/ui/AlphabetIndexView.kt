package com.example.advanceenglishdictionary.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.advanceenglishdictionary.R

class AlphabetIndexView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    interface OnLetterChangeListener {
        fun onLetterChanged(letter: String)
    }

    var onLetterChangeListener: OnLetterChangeListener? = null

    private val alphabet = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    )

    private var selectedIndex = 0

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, android.R.color.darker_gray)
        textSize = 32f
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }

    private val selectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primary)
        textSize = 38f
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.secondary)
        style = Paint.Style.FILL
    }

    fun setSelectedLetter(letter: String) {
        val index = alphabet.indexOfFirst { it.equals(letter, ignoreCase = true) }
        if (index != -1 && index != selectedIndex) {
            selectedIndex = index
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val height = height
        val width = width
        if (height == 0 || width == 0) return

        val singleHeight = height.toFloat() / alphabet.size
        // Adjust font size based on available height
        val fontSize = (singleHeight * 0.75f).coerceAtMost(36f).coerceAtLeast(20f)
        paint.textSize = fontSize
        selectedPaint.textSize = fontSize * 1.25f

        for (i in alphabet.indices) {
            val xPos = width / 2f
            val yPos = singleHeight * i + singleHeight / 2f + fontSize / 3f

            if (i == selectedIndex) {
                // Draw background circle for selected letter
                canvas.drawCircle(xPos, singleHeight * i + singleHeight / 2f, singleHeight / 2.2f, circlePaint)
                canvas.drawText(alphabet[i], xPos, yPos, selectedPaint)
            } else {
                canvas.drawText(alphabet[i], xPos, yPos, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val y = event.y
        val oldChoice = selectedIndex

        val c = (y / height * alphabet.size).toInt().coerceIn(0, alphabet.size - 1)

        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (oldChoice != c) {
                    selectedIndex = c
                    onLetterChangeListener?.onLetterChanged(alphabet[c])
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                invalidate()
            }
        }
        return true
    }
}
