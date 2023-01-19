package com.kyhskola.smoothfightersproject

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.core.content.ContextCompat.startActivity


class Ball(
    var posX: Float,
    var posY: Float,
    var size: Float,
    var speedX: Float,
    var speedY: Float,
    context: Context
) {
    //Declaring variables
    var displayMetrics: DisplayMetrics = context.resources.displayMetrics
    var screenHeight = displayMetrics.heightPixels
    var screenWidth = displayMetrics.widthPixels
    val paint = Paint()
    var mainActivity = context as MainActivity
    var scoreLower = 0
    var scoreUpper = 0

    //Updating the ball position
    fun update() {
        posX += speedX
        posY += speedY

    }

    //Making the ball
    fun draw(canvas: Canvas?) {
        canvas?.drawCircle(posX, posY, size, paint)
    }

    fun checkBounds(bounds: Rect) {
        // check if the ball has hit any of the boundaries of the screen
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
            scoreLower++
            mainActivity.updateText("$scoreLower")


        }
        if (posY + size > bounds.bottom) {
            this.posY = screenHeight/2f
            this.posX = screenWidth/2f
            this.speedX *= 0
            this.speedY *= 0
            scoreUpper++
            mainActivity.updateTextUpper("$scoreUpper")
        }


    }


    //Starts the ball again when someone scores at random speed
    fun startMoving() {
        if (speedX == 0f && speedY == 0f) {
            // Set ball's x-speed to a random value between -5 and 5
            speedX = 10f
            // Set ball's y-speed to a random value between -5 and 5
            speedY = (Math.random().toFloat() * 15f) - (-10f)
        }
    }

    fun win(){
        // check if either player has reached 5 points
        if (scoreUpper == 5 && scoreLower != 5){
            mainActivity.updateText("You Lose!")
            mainActivity.updateTextUpper("You Win!")
            val intent = Intent(mainActivity, StartMenu::class.java)
            mainActivity.startActivity(intent)

        }
        else if (scoreLower == 5 && scoreUpper != 5) {
            mainActivity.updateText("You Win!")
            mainActivity.updateTextUpper("You Lose!")
            val intent = Intent(mainActivity, StartMenu::class.java)
            mainActivity.startActivity(intent)

        }

    }

}