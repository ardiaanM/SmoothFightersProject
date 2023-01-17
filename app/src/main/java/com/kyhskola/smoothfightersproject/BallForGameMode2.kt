package com.kyhskola.smoothfightersproject

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import kotlin.math.min


class BallForGameMode2(
    var posX: Float,
    var posY: Float,
    var size: Float,
    var speedX: Float,
    var speedY: Float,
    context: Context
) {

    val paint = Paint()
    var mainActivity = context as MainActivity
    var lives= 3
    var minusLife = -1

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
            this.posX = 500f
            this.speedX *= 0
            this.speedY *= 0

            lives -= 1
            mainActivity.updateTextLives(lives.toString())
        }
        if (posY + size > bounds.bottom) {
            this.speedY *= -1

        }


    }


    fun startMoving() {
        if (speedX == 0f && speedY == 0f) {
            // Set ball's x-speed to a random value between -5 and 5
            speedX = 15f
            // Set ball's y-speed to a random value between -5 and 5
            speedY = 15f
        }
    }

    fun lose(){
        if (lives == 0){
            mainActivity.updateText("GAME OVER")
            mainActivity.updateTextUpper("GAME OVER")
            val intent = Intent(mainActivity, StartMenu::class.java)
            mainActivity.startActivity(intent)
        }

    }
}