package com.example.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // Cette fonction dessine sur le Canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Dessine un fond cyan
        canvas.drawColor(Color.CYAN)

        // Prépare la peinture pour le texte
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 50f
        }

        // Dessine le texte à l'emplacement spécifié
        canvas?.drawText("Vue personnalisée!", 50f, 50f, paint)
    }
}