package com.kyhskola.smoothfightersproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect


class Ball(
    var posX: Float,
    var posY: Float,
    var size: Float,
    var speedX: Float,
    var speedY: Float,
    context: Context
) {

    val paint = Paint()
    var mainActivity = context as MainActivity
    var scoreLower = 0
    var scoreUpper = 0

    fun update() {
        posX += speedX
        posY += speedY

    }

    fun draw(canvas: Canvas?) {
        canvas?.drawCircle(posX, posY, size, paint)
    }

    fun checkBounds(bounds: Rect) {
        if (posX - size < bounds.left) {
            this.speedX *= -1
        }
        if (posX + size > bounds.right) {
            this.speedX *= -1
        }
        if (posY - size < bounds.top) {
            this.posY = 900f
            this.posX = 700f
            this.speedX *= 0
            this.speedY *= 0
            scoreLower++
            mainActivity.updateText("$scoreLower")


        }
        if (posY + size > bounds.bottom) {
            this.posY = 900f
            this.posX = 500f
            this.speedX *= 0
            this.speedY *= 0
            scoreUpper++
            mainActivity.updateTextUpper("$scoreUpper")
        }


    }


    fun startMoving() {
        if (speedX == 0f && speedY == 0f) {
            // Set ball's x-speed to a random value between -5 and 5
            speedX = (Math.random().toFloat() * 15f) - (-10f)
            // Set ball's y-speed to a random value between -5 and 5
            speedY = (Math.random().toFloat() * 10f) - (-5f)
        }
    }

    fun win(){
        if (scoreUpper == 5 && scoreLower != 5){
            mainActivity.updateText("You Lose!")
            mainActivity.updateTextUpper("You Win!")
        }
        else if (scoreLower == 5 && scoreUpper != 5) {
            mainActivity.updateText("You Win!")
            mainActivity.updateTextUpper("You Lose!")
        }
    }
}