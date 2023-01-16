package com.kyhskola.smoothfightersproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameDesign (context: Context): SurfaceView(context), SurfaceHolder.Callback,Runnable{

    private var thread: Thread? = null
    private var running = false
    lateinit var canvas:Canvas
    private lateinit var ball1:Ball
    private lateinit var player1: Player1
    private lateinit var player2: Player2
    private var bounds = Rect()
    var mainActivity = context as MainActivity
    var score = 0
    var mHolder: SurfaceHolder? = holder
    var playerX = 0f
    var playerX2 = 1f


    var displayMetrics: DisplayMetrics = context.resources.displayMetrics
    var screenHeight = displayMetrics.heightPixels
    var screenWidth = displayMetrics.widthPixels



    init {
        if(mHolder != null){
            mHolder?.addCallback(this)
        }
        setup()
    }

    fun setup(){
        ball1 = Ball(100f, 100f, 35f, 15f, 15f,this.context)
        player1 = Player1(30f, 30f, 5f, 20f, 0f, 30f)
        player2 = Player2(300f, 1795f, 500f, 14f, 1f, 30f)

        ball1.paint.color = Color.WHITE
        player1.paint.color = Color.RED
        player2.paint.color = Color.GREEN
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

        //debug
        println("Ball position: (${ball1.posX}, ${ball1.posY})")
        println("Player 1 position: (${player1.left}, ${player1.top})")
        println("Player 2 position: (${player2.left}, ${player2.top})")


        // Check for collisions with player 1
        if ((ball1.posX > player1.left) && (ball1.posX < player1.right) &&
            (ball1.posY < player1.top + player1.playerHeight) && ((ball1.posY + ball1.size) > player1.top)
        ) {
            // Calculate new direction of the ball
            ball1.speedY = -ball1.speedY
        }




        // Check for collisions with player 2
        if ((ball1.posX < (player2.left + player2.right)) && ((ball1.posX + ball1.size) > player2.left) &&
            (ball1.posY < player2.top + player2.playerHeight) && ((ball1.posY + ball1.size) > player2.top)
        ) {
            // Calculate new direction of the ball
            ball1.speedY = -ball1.speedY
        }

        if (ball1.posX > player2.left) {
            player2.left += player2.speedX
            player2.right += player2.speedX

        } else if (ball1.posX < player2.left) {
            player2.left -= player2.speedX
            player2.right -= player2.speedX
        }



    }

    fun draw(){
        canvas = mHolder!!.lockCanvas()
        canvas.drawColor(Color.BLACK)
        ball1.draw(canvas)
        player1.draw(canvas)
        player2.draw(canvas)
        mHolder!!.unlockCanvasAndPost(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        ball1.startMoving()
        playerX = event!!.x
        return true
    }


    private fun bounceBall(b1: Ball){
        b1.speedY *= -1
        ball1.paint.color = Color.YELLOW
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
            ball1.checkBounds(bounds)
            ball1.win()


        }
    }
}