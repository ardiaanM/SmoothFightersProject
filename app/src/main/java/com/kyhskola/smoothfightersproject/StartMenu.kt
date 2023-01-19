package com.kyhskola.smoothfightersproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_menu)

        //if u press the "Pong" button
        val playButton = findViewById<Button>(R.id.play_pong_button)
        playButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("game_mode", 1)
            startActivity(intent)
        }

        val secondGameMode = findViewById<Button>(R.id.play_surviveball_button)
        secondGameMode.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("game_mode", 2)
            startActivity(intent)
        }
    }
}
