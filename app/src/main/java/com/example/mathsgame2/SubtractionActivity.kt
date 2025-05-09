package com.example.mathsgame2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Locale
import kotlin.random.Random

class SubtractionActivity : AppCompatActivity() {
    lateinit var userScore:TextView
    lateinit var userLife:TextView
    lateinit var userTime:TextView

    lateinit var textQuestion : TextView
    lateinit var editTextAnswer : EditText

    lateinit var buttonOK : Button
    lateinit var buttonNEXT: Button

    lateinit var timer : CountDownTimer
    private val startTimerInMilis : Long= 30000
    var timeLeftInMilis:Long=startTimerInMilis
    var correctAnswer=0
    var Score=0
    var Life=3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subtraction)

        userScore=findViewById(R.id.userScore)
        userLife=findViewById(R.id.userLife)
        userTime=findViewById(R.id.userTime)

        textQuestion=findViewById(R.id.textQuestion)
        editTextAnswer=findViewById(R.id.editTextAnswer2)

        buttonOK=findViewById(R.id.buttonOK)
        buttonNEXT=findViewById(R.id.buttonNEXT)
        supportActionBar!!.title="Subtraction"

        fun updateText(){
            val remainingTime:Int=(timeLeftInMilis/1000).toInt()
            userTime.text=String.format(Locale.getDefault(),"%02d",remainingTime)
        }

        fun pauseTimer(){
            timer.cancel()
        }

        fun resetTimer(){
            timeLeftInMilis=startTimerInMilis
            updateText()
        }

        fun startTimer(){
            timer=object:CountDownTimer(timeLeftInMilis,1000){
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMilis=millisUntilFinished
                    updateText()
                }

                override fun onFinish() {
                    pauseTimer()
                    resetTimer()
                    updateText()
                    Life--
                    userLife.text=Life.toString()
                    textQuestion.text="Sorry, Time Is Up"
                }
            }.start()
        }

        fun gameContinue(){
            val number1 = Random.nextInt(0,100)
            val number2 = Random.nextInt(0,100)
            textQuestion.text="$number1 - $number2"
            correctAnswer=number1-number2
            startTimer()
        }
        gameContinue()
        fun game(){
            editTextAnswer.setText("")
            buttonOK.visibility=View.GONE
            editTextAnswer.visibility=View.GONE
        }

        buttonOK.setOnClickListener {
            val input=editTextAnswer.text.toString()
            if(input==""){
                Toast.makeText(applicationContext,"Please Write An Answer Or Click The Next Button",Toast.LENGTH_LONG).show()
            }
            else{
                pauseTimer()
                game()
                val userAnswer=input.toInt()
                if(userAnswer==correctAnswer){
                    Score+=10
                    textQuestion.text="Congratulations, Your Answer Is Correct"
                    userScore.text=Score.toString()
                    pauseTimer()
                    editTextAnswer.setText("")
                    buttonOK.visibility=View.GONE
                }
                else{
                    Life--
                    textQuestion.text="Sorry Your Answer Is Wrong"
                    userLife.text=Life.toString()
                    pauseTimer()
                    editTextAnswer.setText("")
                    buttonOK.visibility=View.GONE
                }
            }
        }
        buttonNEXT.setOnClickListener {
            pauseTimer()
            resetTimer()
            editTextAnswer.setText("")
            buttonOK.visibility=View.VISIBLE
            editTextAnswer.visibility=View.VISIBLE

            if(Life==0){
                Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
                val intent=Intent(this@SubtractionActivity,ResultActivity::class.java)
                intent.putExtra("Score",Score)
                startActivity(intent)
                finish()
            }
            else{
                gameContinue()
            }
        }
    }
}