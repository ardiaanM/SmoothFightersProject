package com.kyhskola.smoothfightersproject

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameMode2(context: Context) : SurfaceView(context), SurfaceHolder.Callback, Runnable {

    private var thread: Thread? = null
    private var running = false
    lateinit var canvas: Canvas
    private lateinit var surviveball: BallForGameMode2
    private lateinit var playerGame2: PlayerGame2
    private var bounds = Rect()
    var mainActivity = context as MainActivity
    var mHolder: SurfaceHolder? = holder
    var playerX = 0f
    var score = 0
    var highscore = 0

    var displayMetrics = context.resources.displayMetrics
    var screenHeight = displayMetrics.heightPixels
    var screenWidth = displayMetrics.widthPixels


    init {
        if (mHolder != null) {
            mHolder?.addCallback(this)
        }
        setup()
    }

    fun setup() {
        surviveball = BallForGameMode2(100f, 100f, 35f, 15f, 15f, this.context)
        playerGame2 = PlayerGame2 (30f, 1930f, 5f, 20f)


        surviveball.paint.color = Color.WHITE
        playerGame2.paint.color = Color.RED
        highscore = surviveball.retrieveHighscore()
        if (highscore == null) {
            mainActivity.updateTextHighScore("0")
        } else {
            mainActivity.updateTextHighScore(highscore.toString())
        }
    }

    fun start() {
        running = true
        thread = Thread(this)
        thread?.start()
        mainActivity.updateTextLives("3")
    }

    fun stop() {
        running = false
        try {
            thread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun update() {
        surviveball.update()
        playerGame2.update(playerX)

        //debug
        println("Ball position: (${surviveball.posX}, ${surviveball.posY})")
        println("Player 1 position: (${playerGame2.left}, ${playerGame2.top})")


        // Check for collisions with player 1
        if ((surviveball.posX > playerGame2.left) && (surviveball.posX < playerGame2.right) &&
            (surviveball.posY < playerGame2.top + playerGame2.playerHeight) && ((surviveball.posY + surviveball.size) > playerGame2.top)
        ) {
            // Calculate new direction of the ball
            surviveball.speedY = -surviveball.speedY
            surviveball.speedX ++
            surviveball.speedY ++

            score++
            mainActivity.updateText("Score: " + "$score")



            if (score > highscore) {
                highscore = score
                surviveball.updateHighscore(highscore)
                mainActivity.updateTextHighScore("highscore: " + highscore.toString())
            }
        }
        /*
        if ((surviveball.posX < (player1.left + player1.right)) && ((surviveball.posX + surviveball.size) > player1.left) &&
            (surviveball.posY < player1.top + player1.playerHeight) && ((surviveball.posY + surviveball.size) > player1.top)
        ) {
            // Calculate new direction of the ball
            surviveball.speedY = -surviveball.speedY

            score++
            mainActivity.updateText("$score")

            surviveball.speedX += 1
            surviveball.speedY += 1

            if (score > highscore) {
                highscore = score
                surviveball.updateHighscore(highscore)
                mainActivity.updateTextHighScore(highscore.toString())
            }


        }
*/
    }

    fun draw() {
        canvas = mHolder!!.lockCanvas()
        canvas.drawColor(Color.BLACK)
        surviveball.draw(canvas)
        playerGame2.draw(canvas)
        mHolder!!.unlockCanvasAndPost(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        surviveball.startMoving()
        playerX = event!!.x
        return true
    }


    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        bounds = Rect(0, 0, width, height)
        start()
    }


    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()
    }

    override fun run() {
        while (running) {
            update()
            draw()
            surviveball.checkBounds(bounds)
            surviveball.lose()
        }
    }
}