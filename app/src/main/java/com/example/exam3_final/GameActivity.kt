package com.example.exam3_final

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class GameActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout : LinearLayout
    lateinit var new_game : Button
    lateinit var mGameView :GameView
    lateinit var score: TextView
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        new_game = findViewById(R.id.new_game)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        //new

        sharedPreferences = getSharedPreferences("my_pref", 0)
        var highest = sharedPreferences.getInt("highest", 0)

        score.text = "highest Score : " + highest.toString()
        mGameView = GameView(this, this)




        new_game.setOnClickListener{
            mGameView.setBackgroundResource(R.drawable.bg1)
            rootLayout.addView(mGameView)
            new_game.visibility = View.GONE
            score.visibility = View.GONE

        }
    }

    override fun closeGame(mScore: Int) {
        score.text = "Your Score : $mScore"
        new_game.text = "Game Over Play Again!"
        rootLayout.removeView(mGameView)
        new_game.visibility = View.VISIBLE
        score.visibility = View.VISIBLE

        sharedPreferences = getSharedPreferences("my_pref", 0)
        var highest = sharedPreferences.getInt("highest", 0)
        if (mScore > highest) {
            highest = mScore
            val editor = sharedPreferences.edit()
            editor.putInt("highest", highest)
            editor.commit()
        }


        new_game.setOnClickListener {



            recreate()

        }
    }
}


