package com.kyhskola.smoothfightersproject

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.DisplayMetrics
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
    var highscore = 0
    var displayMetrics: DisplayMetrics = context.resources.displayMetrics
    var screenHeight = displayMetrics.heightPixels
    var screenWidth = displayMetrics.widthPixels
    var mContext = context

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
            this.posY = screenHeight/2f
            this.posX = screenWidth/2f
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
            retrieveHighscore()
            val intent = Intent(mainActivity, StartMenu::class.java)
            intent.putExtra("highscore", highscore)
            mainActivity.startActivity(intent)
        }

    }

    fun updateHighscore(newScore: Int) {
        val sharedPref = mContext.getSharedPreferences("highscore", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("highscore", newScore)
        editor.apply()
    }

    fun retrieveHighscore(): Int {
        val sharedPref = this.mContext.getSharedPreferences("highscore", Context.MODE_PRIVATE)
        highscore = sharedPref.getInt("highscore", 0)
        return highscore
    }
}
