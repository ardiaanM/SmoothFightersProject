package com.kyhskola.smoothfightersproject

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Player1 (var posX: Float, var posY: Float, var size: Float, var speedX: Float, var speedY: Float){

    val paint = Paint()

    fun draw (canvas: Canvas?){
        canvas?.drawRect(posX,posY,size,speedX,paint)
    }

    fun update() {
        posX += speedX
        posY += speedY
    }


}