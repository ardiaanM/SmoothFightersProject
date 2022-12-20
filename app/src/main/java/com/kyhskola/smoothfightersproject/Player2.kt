package com.kyhskola.smoothfightersproject

import android.graphics.Canvas
import android.graphics.Paint

class Player2 (var posX: Float, var posY: Float, var size: Float, var speedX: Float, var speedY: Float, var playerHeight: Float) {


    val paint = Paint()


    fun draw(canvas: Canvas?) {
        canvas?.drawRect(posX, 1795f, size, 1950f, paint)
    }


    fun update(pos: Float) {
        posX = pos - 100
        size = pos + 100
    }


}