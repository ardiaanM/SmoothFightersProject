package com.kyhskola.smoothfightersproject

import android.graphics.Canvas
import android.graphics.Paint

abstract class Player(var left: Float, var top: Float, var right: Float, var speedX: Float, var speedY: Float, val playerHeight: Float) {
    val paint = Paint()
    abstract fun draw(canvas: Canvas?)
}

class Player1(left: Float, top: Float, right: Float, speedX: Float, speedY: Float, playerHeight: Float) : Player(left, top, right, speedX, speedY, playerHeight) {
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(left, top, right, 0f, paint)
    }

    //Makes the paddle move left and right
    fun update(pos: Float) {
        left = pos - 100
        right = pos + 100
    }
}

class Player2(left: Float, top: Float, right: Float, speedX: Float, speedY: Float, playerHeight: Float) : Player(left, top, right, speedX, speedY, playerHeight) {
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(left, top, right, 1950f, paint)
    }
}
