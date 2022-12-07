package com.kyhskola.smoothfightersproject

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Player1 (var posX: Float, var posY: Float, var size: Float, var speedX: Float, var speedY: Float){

    val paint = Paint()

    fun draw (canvas: Canvas?){
        canvas?.drawRect(posX,posY,size,10f,paint)
        println("Hej")
    }


    fun update(pos: Float) {
        posX = pos-200
        size = pos + 200
    }



}