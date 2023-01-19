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
    private lateinit var player1: Player1
    private var bounds = Rect()
    var mainActivity = context as MainActivity
    var mHolder: SurfaceHolder? = holder
    var playerX = 0f
    var score = 0
    var highscore = 0

    //var displayMetrics = context.resources.displayMetrics
    //var screenHeight = displayMetrics.heightPixels
    //var screenWidth = displayMetrics.widthPixels

    //Creating the game surface
    init {
        if (mHolder != null) {
            mHolder?.addCallback(this)
        }
        setup()
    }

    //Creating objects for the game
    fun setup() {
        surviveball = BallForGameMode2(100f, 100f, 35f, 15f, 15f, this.context)
        player1 = Player1(30f, 30f, 5f, 20f, 0f, 30f)


        surviveball.paint.color = Color.BLACK
        player1.paint.color = Color.WHITE
        highscore = surviveball.retrieveHighscore()
        if (highscore == null) {
            mainActivity.updateTextHighScore("0")
        } else {
            mainActivity.updateTextHighScore(highscore.toString())
        }
    }

    //Failsafe
    //Starts the game loop
    fun start() {
        running = true
        thread = Thread(this)
        thread?.start()
        mainActivity.updateTextLives("Lives: 3")
    }
    //Failsafe
    //Stops the game loop, join will wait for start to finnsh
    fun stop() {
        running = false
        try {
            thread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    fun update() {
        //Makes the ball move left and right, up, down
        surviveball.update()
        //Makes the paddle move left and right
        player1.update(playerX)

        //debug
        println("Ball position: (${surviveball.posX}, ${surviveball.posY})")
        println("Player 1 position: (${player1.left}, ${player1.top})")


        // Check for collisions with player 1
        if ((surviveball.posX > player1.left) && (surviveball.posX < player1.right) &&
            (surviveball.posY < player1.top + player1.playerHeight) && ((surviveball.posY + surviveball.size) > player1.top)
        ) {
            // Calculate new direction of the ball
            surviveball.speedY = -surviveball.speedY

            score++
            mainActivity.updateText("Score: " + "$score")

            surviveball.speedX += 1
            surviveball.speedY += 1

            if (score > highscore) {
                highscore = score
                surviveball.updateHighscore(highscore)
                mainActivity.updateTextHighScore("Top Score : " + highscore.toString())
            }
        }
    }

    //Paints up the game objects
    fun draw() {
        canvas = mHolder!!.lockCanvas()
        canvas.drawColor(Color.DKGRAY)
        surviveball.draw(canvas)
        player1.draw(canvas)
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