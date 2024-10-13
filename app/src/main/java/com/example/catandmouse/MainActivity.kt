package com.example.catandmouse


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStart = findViewById<Button>(R.id.buttonStart)
        val buttonStatistics = findViewById<Button>(R.id.buttonCount)
        val seekBarCount = findViewById<SeekBar>(R.id.seekBarCount)
        val seekBarVelocity = findViewById<SeekBar>(R.id.seekBarVelocity)
        val seekBarSize = findViewById<SeekBar>(R.id.seekBarSize)

        seekBarCount.progress=1
        seekBarVelocity.progress=5
        seekBarSize.progress=50

        val textViewCount = findViewById<TextView>(R.id.textViewCount)
        val textViewVelocity = findViewById<TextView>(R.id.textViewVelocity)
        val textViewSize = findViewById<TextView>(R.id.textViewSize)

        seekBarCount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewCount.text = "Количество: $progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarVelocity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewVelocity.text = "Скорость: $progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewSize.text = "Размер: $progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        buttonStart.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)

            val mouseCount = seekBarCount.progress
            val velocity = seekBarVelocity.progress
            val size = seekBarSize.progress

            intent.putExtra("mouseCount", mouseCount)
            intent.putExtra("velocity", velocity)
            intent.putExtra("size", size)
            startActivity(intent)
        }

        buttonStatistics.setOnClickListener {
            val intent = Intent(this, ScoreHistoryActivity::class.java)
            startActivity(intent)
        }

    }
}


