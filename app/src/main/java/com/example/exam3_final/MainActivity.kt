package com.example.exam3_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Explicit Intent
        val startButton = findViewById<Button>(R.id.start)

        startButton.setOnClickListener{
            val game = Intent(this, GameActivity::class.java )
            startActivity(game)
        }
    }
}