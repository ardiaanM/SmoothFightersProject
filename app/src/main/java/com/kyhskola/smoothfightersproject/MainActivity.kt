package com.kyhskola.smoothfightersproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.commit
import com.kyhskola.smoothfightersproject.databinding.ActivityMainBinding

// Github: https://github.com/ardiaanM/SmoothFightersProject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameMode = intent.getIntExtra("game_mode", 0)
        if (gameMode == 1) {
            supportFragmentManager.commit {
                add(R.id.frame_content, GameFragment())
            }
        } else if (gameMode == 2) {
            supportFragmentManager.commit {
                add(R.id.frame_content, GameFragmentSurvive())
            }
        }

    }

    fun updateText(str: String){
        runOnUiThread(Runnable {
            binding.textView.text = str
        })

    }

    fun updateTextUpper(str: String){
        runOnUiThread(Runnable {
            binding.textViewUpper.text = str
        })

    }

    fun updateTextLives(str: String){
        runOnUiThread(Runnable {
            binding.textViewUpper.text = str
        })

    }

    fun updateTextScore(str: String){
        runOnUiThread(Runnable {
            binding.textView.text
        })
    }
}
