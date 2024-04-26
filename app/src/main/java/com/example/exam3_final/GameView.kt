package com.example.exam3_final

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import java.lang.Exception

class GameView(var c: GameActivity, var gameTask:GameTask): View(c) {
    private var myPaint: Paint? = null
    private var level = 1
    private var time = 0
    private var score = 0
    private var mainPosition = 0
    private val monsters = ArrayList<HashMap<String,Any>>()

    var viewWidth = 0
    var viewHeight = 0
    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if(time % 700 < 10 +level){
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            monsters.add(map)
        }

        time = time + 10 + level
        val mainWidth = viewWidth / 5
        val mainHeight = mainWidth + 10
        myPaint!!.style = Paint.Style.FILL
        val main = resources.getDrawable(R.drawable.main, null)
        main.setBounds(
            mainPosition * viewWidth/ 3 + viewWidth / 15 + 25,
            viewHeight-150 - mainHeight,
            mainPosition * viewWidth / 3 + viewWidth / 15 + mainWidth - 25,
            viewHeight - 150
        )
        main.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for(i in monsters.indices){
            try{
                val monsterX = monsters[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var monsterY = time - monsters[i]["startTime"] as Int
                val monster = resources.getDrawable(R.drawable.monster, null)

                monster.setBounds(
                    monsterX + 25, monsterY - mainHeight, monsterX + mainWidth - 25, monsterY
                )
                monster.draw(canvas)
                if(monsters[i]["lane"] as Int == mainPosition){
                    if(monsterY > viewHeight - 2 - mainHeight
                        && monsterY < viewHeight - 2){
                        gameTask.closeGame(score)
                    }
                }
                if(monsterY > viewHeight + mainHeight){
                    monsters.removeAt(i)
                    score++
                    level = 1 + Math.abs(score / 8)
                    if(score > highScore){
                        highScore = score
                    }
                }
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 50f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Level : $level", 380f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if(x1 < viewWidth / 2){
                    if(mainPosition > 0){
                        mainPosition--
                    }
                }
                if(x1 > viewWidth / 2){
                    if(mainPosition < 2){
                        mainPosition++
                    }
                }
                invalidate()
            }

            MotionEvent.ACTION_UP -> {}
        }
        return true
    }
}