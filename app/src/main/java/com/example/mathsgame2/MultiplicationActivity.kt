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
import java.util.Random

class MultiplicationActivity : AppCompatActivity() {
    lateinit var textScore : TextView
    lateinit var textLife : TextView
    lateinit var textTime : TextView

    lateinit var questionBox : TextView
    lateinit var answer : EditText

    lateinit var buttonOK1:Button
    lateinit var buttonNext1 : Button

    lateinit var timer : CountDownTimer
    private val startTimerInMilis : Long= 30000
    var timeLeftInMilis:Long=startTimerInMilis

    var correctAnswer=0
    var userScore = 0
    var userLife=3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplication)
        textScore=findViewById(R.id.textScore)
        textLife=findViewById(R.id.textLife)
        textTime=findViewById(R.id.textTime)
        questionBox=findViewById(R.id.QuestionBox)
        answer=findViewById(R.id.Answer)

        buttonOK1=findViewById(R.id.buttonOK1)
        buttonNext1=findViewById(R.id.buttonNext1)

        fun pauseTimer(){
            timer.cancel()
        }
        fun updateText(){
            val remainingTime : Int= (timeLeftInMilis/1000).toInt()
            textTime.text= String.format(Locale.getDefault(),"%02d",remainingTime)
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
                    userLife--
                    textLife.text=userLife.toString()
                    questionBox.text="Sorry, Time's Up"
                }
            }.start()
        }

        fun gameContinue(){
            val number1 = kotlin.random.Random.nextInt(2, 20)
            val number2 = kotlin.random.Random.nextInt(1, 20)
            questionBox.text="$number1*$number2"
            correctAnswer=number1*number2
            startTimer()
        }
        gameContinue()
        fun game(){
            answer.setText("")
            buttonOK1.visibility=View.GONE
            answer.visibility=View.GONE
        }

        buttonOK1.setOnClickListener {
            val input=answer.text.toString()
            if(input==""){
                Toast.makeText(applicationContext,"Please Write An Answer Or Click The Next Button",Toast.LENGTH_LONG).show()
            }
            else{
                pauseTimer()
                game()
                val userAnswer=input.toInt()
                if(userAnswer==correctAnswer){
                    userScore+=10
                    questionBox.text="Congratulations, Your Answer Is Correct"
                    textScore.text=userScore.toString()
                }
                else{
                    userLife--
                    questionBox.text="Sorry, Your Answer Is Wrong"
                    textLife.text=userLife.toString()
                }

            }
        }
        buttonNext1.setOnClickListener {
            pauseTimer()

            resetTimer()

            answer.setText("")
            buttonOK1.visibility=View.VISIBLE
            answer.visibility=View.VISIBLE
            if(userLife==0){
                Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
                val intent=Intent(this@MultiplicationActivity,ResultActivity::class.java)
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