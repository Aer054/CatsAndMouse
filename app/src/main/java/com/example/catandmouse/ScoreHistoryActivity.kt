package com.example.catandmouse

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScoreHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_history)

        val dbHelper = DbHelper(this)
        val clicks = dbHelper.getLast10Clicks()
        val backButton: Button = findViewById(R.id.buttonBack2)
        backButton.setOnClickListener {
            finish()
        }
        val adapter = Adapter(clicks)

        val list = findViewById<RecyclerView>(R.id.List)
        list.layoutManager = LinearLayoutManager(this)

        list.adapter = adapter
    }

    class Adapter(private val clicks: List<Clicks>) :
        RecyclerView.Adapter<Adapter.Holder>() {
        class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var cscreenClickScore: TextView = itemView.findViewById(R.id.screenClickScore)
            var cmouseClickScore: TextView = itemView.findViewById(R.id.mouseClickScore)
            var cclickPocentage: TextView = itemView.findViewById(R.id.clickPocentage)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_score_history, parent, false)
            return Holder(view)
        }

        override fun getItemCount(): Int {
            return clicks.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val clickData = clicks[position]
            holder.cscreenClickScore.text = "Нажатий по экрану: ${clickData.screenClick}"
            holder.cmouseClickScore.text = "Попаданий: ${clickData.mouseClick}"

            val screenClick = clickData.screenClick.toIntOrNull() ?: 0
            val mouseClick = clickData.mouseClick.toIntOrNull() ?: 0
            val clickPercentage = if (screenClick > 0) (mouseClick * 100) / screenClick else 0
            holder.cclickPocentage.text = "Процентное попадание: $clickPercentage%"

        }
    }

}