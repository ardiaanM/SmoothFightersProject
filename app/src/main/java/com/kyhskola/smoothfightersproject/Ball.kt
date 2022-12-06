package com.kyhskola.smoothfightersproject

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Ball(var posX: Float, var posY: Float, var size: Float, var speedX: Float, var speedY: Float) {

    val paint = Paint()

    fun update(){
        posX += speedX
        posY += speedY
    }

    fun draw (canvas: Canvas?){
        canvas?.drawCircle(posX,posY,size,paint)
    }

    fun checkBounds(bounds: Rect){
        if (posX-size < bounds.left){
            this.speedX *= -1
        }
        if (posX+size > bounds.right){
            this.speedX *= -1
        }
        if (posY-size < bounds.top){
            this.speedY *= -1
        }
        if (posY+size > bounds.bottom){
            this.speedY *= -1
        }
    }
}