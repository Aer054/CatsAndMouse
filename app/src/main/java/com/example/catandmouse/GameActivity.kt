package com.example.catandmouse


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Random

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {

    private val random = Random()
    private val handler = Handler()
    private var screenWidth = 0
    private var screenHeight = 0

    private var velocityX = 10f
    private var velocityY = 10f
    private lateinit var mouseArray: ArrayList<ImageView>
    private var mouseTapScore = 0
    private var tapScore = 0
    private lateinit var mouseTapScoreTextView: TextView
    private lateinit var tapScoreTextView: TextView

    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        dbHelper = DbHelper(this)

        val mouseCount = intent.getIntExtra("mouseCount", 1)
        val velocity = intent.getIntExtra("velocity", 10)
        val size = intent.getIntExtra("size", 50)

        velocityX = velocity.toFloat()
        velocityY = velocity.toFloat()


        val rootLayout: View = findViewById(android.R.id.content)
        mouseArray = ArrayList()

        tapScoreTextView = findViewById(R.id.textViewTapSore)
        mouseTapScoreTextView = findViewById(R.id.textViewMouseTapScore)
        updateScore()

        rootLayout.post {
            screenWidth = rootLayout.width
            screenHeight = rootLayout.height

            for (i in 0 until mouseCount) {
                addMouse(rootLayout, size)
            }

            startMovingMouse()
        }

        rootLayout.setOnTouchListener { _, _ ->
            tapScore++
            updateScore()
            false
        }
    }
    private fun addMouse(rootLayout: View, size: Int) {
        val mouse = ImageView(this)
        mouse.setImageResource(R.drawable.baseline_mouse_24)

        mouse.layoutParams = ViewGroup.LayoutParams(size, size)

        val startX = random.nextInt(screenWidth - size)
        val startY = random.nextInt(screenHeight - size)
        mouse.x = startX.toFloat()
        mouse.y = startY.toFloat()

        (rootLayout as ViewGroup).addView(mouse)
        mouseArray.add(mouse)

        val randomVelocityX = velocityX * if (random.nextBoolean()) 1 else -1
        val randomVelocityY = velocityY * if (random.nextBoolean()) 1 else -1


        mouse.tag = Pair(randomVelocityX, randomVelocityY)

        mouse.setOnClickListener {
            tapScore++
            mouseTapScore++
            updateScore()
        }

        val backButton: Button = findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }
    }
    private fun updateScore() {
        tapScoreTextView.text = "Нажатий по экроану: $tapScore"
        mouseTapScoreTextView.text="Попаданий: $mouseTapScore"
    }
    private fun startMovingMouse() {
        handler.post(object : Runnable {
            override fun run() {
                for (mouse in mouseArray) {
                    moveMouse(mouse)
                }
                handler.postDelayed(this, 30)
            }
        })
    }

    private fun moveMouse(mouse: ImageView) {
        val currentX = mouse.x
        val currentY = mouse.y


        val (velocityX, velocityY) = mouse.tag as Pair<Float, Float>

        var newX = currentX + velocityX
        var newY = currentY + velocityY

        var newVelocityX = velocityX
        var newVelocityY = velocityY

        if (newX < 0 || newX + mouse.width > screenWidth) {
            newVelocityX = -velocityX
            newX = currentX + newVelocityX
        }

        if (newY < 0 || newY + mouse.height > screenHeight) {
            newVelocityY = -velocityY
            newY = currentY + newVelocityY
        }

        mouse.x = newX
        mouse.y = newY

        mouse.tag = Pair(newVelocityX, newVelocityY)
    }
    override fun onPause() {
        super.onPause()
        dbHelper.insertClickData(tapScore, mouseTapScore)
    }

}

