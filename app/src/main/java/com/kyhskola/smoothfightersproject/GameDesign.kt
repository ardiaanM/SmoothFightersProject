package com.kyhskola.smoothfightersproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Rect.intersects
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameDesign (context: Context): SurfaceView(context), SurfaceHolder.Callback,Runnable{

    private var thread: Thread? = null
    private var running = false
    lateinit var canvas:Canvas
    private lateinit var ball1:Ball
    private lateinit var player1: Player1
    private var bounds = Rect()
    var mainActivity = context as MainActivity
    var score = 0
    var mHolder: SurfaceHolder? = holder
    var playerX = 0f


    init {
        if(mHolder != null){
            mHolder?.addCallback(this)
        }
        setup()
    }

    fun setup(){
        ball1 = Ball(100f, 100f, 35f, 20f, 20f)
        player1 = Player1(30f, 30f, 1f, 0f, 0f, 30f)

        ball1.paint.color = Color.BLACK
        player1.paint.color = Color.RED
    }

    fun start(){
        running = true
        thread = Thread(this)
        thread?.start()
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
        ball1.update()
        player1.update(playerX)

        // Check for collisions with the paddle
        if ((ball1.posX < (player1.posX + player1.size)) && ((ball1.posX + ball1.size) > player1.posX) &&
            (ball1.posY < player1.posY + player1.playerHeight) && ((ball1.posY + ball1.size) > player1.posY)
        ) {

            // Calculate new direction of the ball
            ball1.speedY = -ball1.speedY

            score++
            mainActivity.updateText("score: $score")
        }


    }

    fun draw(){
        canvas = mHolder!!.lockCanvas()
        canvas.drawColor(Color.BLUE)
        ball1.draw(canvas)
        player1.draw(canvas)
        mHolder!!.unlockCanvasAndPost(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        playerX = event!!.x
        return true
    }


    private fun bounceBall(b1: Ball , p1: Player1){
        b1.speedY *= -1
        ball1.paint.color = Color.YELLOW
    }

    fun intersects(b1: Ball, p1: Player1){
        if (Math.sqrt(Math.pow(b1.posX-p1.posX.toDouble(),2.0)+Math.pow(b1.posY-p1.posY.toDouble(), 2.0))<=b1.size+p1.size){
            bounceBall(b1,p1)

            score++
            mainActivity.updateText("score: $score")
        }
    }



    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        bounds = Rect(0,0,width,height)
        start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()
    }

    override fun run() {
        while(running){
            update()
            draw()
            //intersects(ball1, player1)
            ball1.checkBounds(bounds)

        }
    }
}