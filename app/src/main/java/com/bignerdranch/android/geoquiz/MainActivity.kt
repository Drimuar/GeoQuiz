package com.bignerdranch.android.geoquiz


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders


private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            if (quizViewModel.currentIndex + 1 == quizViewModel.questionBankSize) {
                val ma2intent = Intent(this@MainActivity, MainActivity2::class.java)
                ma2intent.putExtra("testNameData", quizViewModel.correctAnswer);
                startActivity(ma2intent)
            }
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            if (quizViewModel.currentIndex + 1 == quizViewModel.questionBankSize) {
                val ma2intent = Intent(this@MainActivity, MainActivity2::class.java)
                ma2intent.putExtra("testNameData", quizViewModel.correctAnswer);
                startActivity(ma2intent)
            }
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            trueButton.isEnabled = true
            falseButton.isEnabled = true
            if (quizViewModel.currentIndex + 1 == quizViewModel.questionBankSize) {
                nextButton.isEnabled = false
            }
        }
        updateQuestion()
    }


    override fun onStart() {
                super.onStart()
                Log.d(TAG, "onStart() called")
            }
     
    override fun onResume() {
            super.onResume()
            Log.d(TAG, "onResume() called")
        }

    override fun onPause() {
            super.onPause()
            Log.d(TAG, "onPause() called")
        }

    override  fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
            super.onStop()
            Log.d(TAG, "onStop() called")
        }
     
    override fun onDestroy() {
            super.onDestroy()
            Log.d(TAG, "onDestroy() called")
        }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.countCorrectAnswer()
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        .show()
    }
}