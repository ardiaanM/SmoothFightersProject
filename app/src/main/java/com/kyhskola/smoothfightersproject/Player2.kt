package com.kyhskola.smoothfightersproject

import android.graphics.Canvas
import android.graphics.Paint

class Player2 (var left: Float, var top: Float, var right: Float, var speedX: Float, var speedY: Float, var playerHeight: Float) {


    val paint = Paint()


    fun draw(canvas: Canvas?) {
        canvas?.drawRect(left, 1795f, right, 1950f, paint)
    }


    fun update(pos: Float) {
        left = pos - 100
        right = pos + 100
    }


}