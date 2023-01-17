package com.kyhskola.smoothfightersproject

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameMode2 (context: Context): SurfaceView(context), SurfaceHolder.Callback,Runnable{

    private var thread: Thread? = null
    private var running = false
    lateinit var canvas:Canvas
    private lateinit var surviveball: BallForGameMode2
    private lateinit var player1: Player1
    private var bounds = Rect()
    var mainActivity = context as MainActivity
    var mHolder: SurfaceHolder? = holder
    var playerX = 0f
    var score = 0
    var lives = 3

    var displayMetrics = context.resources.displayMetrics
    var screenHeight = displayMetrics.heightPixels
    var screenWidth = displayMetrics.widthPixels


    init {
        if(mHolder != null){
            mHolder?.addCallback(this)
        }
        setup()
    }

    fun setup(){
        surviveball = BallForGameMode2(100f, 100f, 35f, 15f, 15f,this.context)
        player1 = Player1(30f, 30f, 5f, 20f, 0f, 30f)


        surviveball.paint.color = Color.BLACK
        player1.paint.color = Color.RED
    }

    fun start(){
        running = true
        thread = Thread(this)
        thread?.start()
        mainActivity.updateTextLives("3")
    }

    fun stop(){
        running = false
        try{
            thread?.join()
        }catch (e:InterruptedException){
            e.printStackTrace()
        }
    }

    fun update(){
        surviveball.update()
        player1.update(playerX)

        //debug
        println("Ball position: (${surviveball.posX}, ${surviveball.posY})")
        println("Player 1 position: (${player1.left}, ${player1.top})")


        // Check for collisions with player 1
        if ((surviveball.posX < (player1.left + player1.right)) && ((surviveball.posX + surviveball.size) > player1.left) &&
            (surviveball.posY < player1.top + player1.playerHeight) && ((surviveball.posY + surviveball.size) > player1.top)
        ) {
            // Calculate new direction of the ball
            surviveball.speedY = -surviveball.speedY

            score++
            mainActivity.updateText("$score")

            surviveball.speedX += 1
            surviveball.speedY += 1

        }

    }

    fun draw(){
        canvas = mHolder!!.lockCanvas()
        canvas.drawColor(Color.BLUE)
        surviveball.draw(canvas)
        player1.draw(canvas)
        mHolder!!.unlockCanvasAndPost(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        surviveball.startMoving()
        playerX = event!!.x
        return true
    }


    private fun bounceBall(b1: BallForGameMode2){
        b1.speedY *= -1
        surviveball.paint.color = Color.YELLOW
    }




    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        bounds = Rect(0,0,screenWidth,screenHeight)
        start()
    }


    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()
    }

    override fun run() {
        while(running){
            update()
            draw()
            surviveball.checkBounds(bounds)
            surviveball.lose()

        }
    }
}