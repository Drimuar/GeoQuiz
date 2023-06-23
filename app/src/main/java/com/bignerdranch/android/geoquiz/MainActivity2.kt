package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class MainActivity2 : AppCompatActivity() {
    private lateinit var textView1: TextView
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this)[QuizViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val data = intent.extras!!.getInt("testNameData")
        findViewById<TextView>(R.id.text_view).apply {
            text = data.toString()
        }
    }
}