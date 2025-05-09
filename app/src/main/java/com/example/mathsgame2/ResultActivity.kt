package com.example.mathsgame2

import android.content.Intent
import android.content.Intent.ACTION_MAIN
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class ResultActivity : AppCompatActivity() {
    lateinit var congratulations : TextView
    lateinit var score : TextView
    lateinit var playAgain : Button
    lateinit var exitButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        congratulations=findViewById(R.id.congratulation)
        score=findViewById(R.id.score)
        playAgain=findViewById(R.id.playAgainButton)
        exitButton=findViewById(R.id.exitButton)

        val score1=intent.getIntExtra("Score",0)
        score.text="Your Score $score1"
        playAgain.setOnClickListener {
            val intent= Intent(this@ResultActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        exitButton.setOnClickListener {
            val intent=Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}