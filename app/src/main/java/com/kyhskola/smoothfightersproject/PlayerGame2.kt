package com.kyhskola.smoothfightersproject

import android.graphics.Canvas
import android.graphics.Paint

class PlayerGame2 (var left: Float, var top: Float, var right: Float, var speedX: Float, var speedY: Float, var playerHeight: Float) {


    val paint = Paint()


    fun draw(canvas: Canvas?) {
        canvas?.drawRect(left, top, right, 2070f, paint)
    }


    fun update(pos: Float) {
        left = pos - 100
        right = pos + 100
    }


}
