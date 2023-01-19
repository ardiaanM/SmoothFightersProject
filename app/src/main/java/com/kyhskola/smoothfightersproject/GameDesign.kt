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
    var mHolder: SurfaceHolder? = holder
    var playerX = 0f


    //Creates the game surface
    init {
        if(mHolder != null){
            mHolder?.addCallback(this)
        }
        setup()
    }

    //initializes the game objects such as ball, player1 and player2, and sets their color.
    fun setup(){
        ball1 = Ball(100f, 100f, 35f, 10f, 10f,this.context)
        player1 = Player1(30f, 30f, 5f, 20f, 0f, 30f)
        player2 = Player2(300f, 1920f, 500f, 10f, 0f, 30f)

        ball1.paint.color = Color.WHITE
        player1.paint.color = Color.GREEN
        player2.paint.color = Color.RED
    }

    //starts a new thread for the game loop, where the game objects are updated and drawn on the screen
    fun start(){
        running = true
        thread = Thread(this)
        thread?.start()
    }
    //method stops the game loop and ends the thread
    fun stop(){
        running = false
        try{
            thread?.join()
        }catch (e:InterruptedException){
            e.printStackTrace()
        }
    }

    //updates the position of the game objects based on their speed and direction
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
            ball1.speedX += 2
            ball1.speedY += 2
        }




        // Check for collisions with player 2
        if ((ball1.posX < (player2.left + player2.right)) && ((ball1.posX + ball1.size) > player2.left) &&
            (ball1.posY < player2.top + player2.playerHeight) && ((ball1.posY + ball1.size) > player2.top)
        ) {
            // Calculate new direction of the ball
            ball1.speedY = -ball1.speedY
            ball1.speedX += 2
        }

        //AI - moves the paddle
        if (ball1.posX > player2.right) {
            player2.left += player2.speedX
            player2.right += player2.speedX

        } else if (ball1.posX < player2.left) {
            player2.left -= player2.speedX
            player2.right -= player2.speedX
        }



    }

    //draw the game objects on the screen
    fun draw(){
        canvas = mHolder!!.lockCanvas()
        canvas.drawColor(Color.BLACK)
        ball1.draw(canvas)
        player1.draw(canvas)
        player2.draw(canvas)
        mHolder!!.unlockCanvasAndPost(canvas)
    }

    //Moving the raquete and starts the ball again
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        ball1.startMoving()
        playerX = event!!.x
        return true
    }


    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        bounds = Rect(0,0,width,height)
        start()
    }

    //Stops the game
    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()
    }

    //while the game runs
    override fun run() {
        while(running){
            update()
            draw()
            ball1.checkBounds(bounds)
            ball1.win()


        }
    }
}