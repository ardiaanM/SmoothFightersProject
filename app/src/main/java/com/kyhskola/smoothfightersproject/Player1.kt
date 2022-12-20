package com.kyhskola.smoothfightersproject

import android.graphics.Canvas
import android.graphics.Paint

class Player1(var posX: Float, var posY: Float, var size: Float, var speedX: Float, var speedY: Float, val playerHeight: Float){

    val paint = Paint()

    fun draw (canvas: Canvas?){
        canvas?.drawRect(posX,posY,size,0f,paint)
    }


    fun update(pos: Float) {
        posX = pos - 100
        size = pos + 100
    }




}