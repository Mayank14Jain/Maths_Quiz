package com.example.mathsgame2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var addition:Button
    lateinit var subtraction:Button
    lateinit var multiplication:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addition=findViewById(R.id.addition)
        subtraction=findViewById(R.id.subtraction)
        multiplication=findViewById(R.id.multiplication)

        addition.setOnClickListener {
            val intent=Intent(this@MainActivity,GameActivity2::class.java)
            startActivity(intent)
        }
        subtraction.setOnClickListener {
            val intent=Intent(this@MainActivity,SubtractionActivity::class.java)
            startActivity(intent)
        }
        multiplication.setOnClickListener {
            val intent=Intent(this@MainActivity,MultiplicationActivity::class.java)
            startActivity(intent)
        }
    }
}