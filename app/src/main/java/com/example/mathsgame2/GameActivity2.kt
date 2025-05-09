package com.example.mathsgame2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.util.Locale
import kotlin.random.Random

class GameActivity2 : AppCompatActivity() {
    lateinit var textScore: TextView
    lateinit var textLife: TextView
    lateinit var textTime: TextView

    lateinit var textQuestion: TextView
    lateinit var editTextAnswer: EditText

    lateinit var buttonOK: Button
    lateinit var buttonNext: Button
    lateinit var timer : CountDownTimer
    private val startTimerInMilis:Long=60000

    var timeLeftInMilis : Long = startTimerInMilis
    var correctAnswer = 0
    var userScore = 0
    var userLife = 3
    lateinit var linear : LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game2)
        supportActionBar!!.title="Addition"
        textScore = findViewById(R.id.textView2)
        textLife = findViewById(R.id.textView4)
        textTime = findViewById(R.id.textView6)
        textQuestion = findViewById(R.id.textView7)
        editTextAnswer = findViewById(R.id.editTextNumber)
        buttonOK = findViewById(R.id.button)
        buttonNext = findViewById(R.id.button2)

        linear=findViewById(R.id.linear)


        fun updateText(){
            val remainingTime:Int=(timeLeftInMilis/1000).toInt()
            textTime.text =String.format(Locale.getDefault(),"%02d",remainingTime)
        }
        fun pauseTimer(){
            timer.cancel()
        }
        fun resetTimer(){
            timeLeftInMilis=startTimerInMilis
            updateText()
        }
        fun startTimer() {
            timer = object : CountDownTimer(timeLeftInMilis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMilis = millisUntilFinished
                    updateText()
                }

                override fun onFinish() {
                    pauseTimer()
                    resetTimer()
                    updateText()
                    userLife--
                    textLife.text = userLife.toString()
                    textQuestion.setText("Sorry, Time is up!")
                }
            }.start()
        }
        fun gameContinue() {
            val number1 = Random.nextInt(0, 100)
            val number2 = Random.nextInt(0, 100)

            textQuestion.setText("$number1 + $number2")

            correctAnswer = number1 + number2
            startTimer()
        }
        gameContinue()

        fun game(){
            editTextAnswer.setText("")
            buttonOK.visibility=View.GONE
            editTextAnswer.visibility=View.GONE
        }


        buttonOK.setOnClickListener {
            val input = editTextAnswer.text.toString()
            if (input == "") {
                Toast.makeText(
                    applicationContext,
                    "Please Write An Answer Or Click The Next Button",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                pauseTimer()
                val userAnswer=input.toInt()
                game()
                if(userAnswer==correctAnswer){
                    userScore=userScore+10
                    textQuestion.setText("Congratulations,Your answer is correct.")
                    textScore.text=userScore.toString()
                    pauseTimer()
                }
                else{
                    userLife--
                    textQuestion.setText("Sorry, Your answer is wrong")
                    textLife.text=userLife.toString()
                    pauseTimer()
                }
            }
        }
        buttonNext.setOnClickListener {
            pauseTimer()
            resetTimer()
            editTextAnswer.setText("")
            editTextAnswer.visibility=View.VISIBLE
            buttonOK.visibility=View.VISIBLE
            if (userLife==0){
                Toast.makeText(applicationContext,"Game Over !",Toast.LENGTH_LONG).show()
                val intent= Intent(this@GameActivity2,ResultActivity::class.java)
                intent.putExtra("Score",userScore)
                startActivity(intent)
                finish()
            }
            else{
                gameContinue()
            }
        }
    }
}