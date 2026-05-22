package com.example.advanceenglishdictionary.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advanceenglishdictionary.R
import com.example.advanceenglishdictionary.adapters.QuizOption
import com.example.advanceenglishdictionary.adapters.QuizOptionAdapter
import com.example.advanceenglishdictionary.dao.QuizDao
import com.example.advanceenglishdictionary.databinding.ActivityQuizBinding
import com.example.advanceenglishdictionary.models.QuizQuestion

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizDao: QuizDao
    private lateinit var optionAdapter: QuizOptionAdapter
    
    private var questions: List<QuizQuestion> = listOf()
    private var currentQuestionIndex = 0
    private var correctCount = 0
    private var incorrectCount = 0
    private var timer: CountDownTimer? = null
    
    private val QUIZ_DURATION = 10000L // 10 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizDao = QuizDao(this)
        val subLevel = intent.getIntExtra("SUB_LEVEL", 1)
        
        loadQuestions(subLevel)
        setupRecyclerView()
        
        if (questions.isNotEmpty()) {
            showQuestion(0)
        } else {
            finish() // Or show error
        }
    }

    private fun loadQuestions(subLevel: Int) {
        questions = quizDao.getQuestions(subLevel)
    }

    private fun setupRecyclerView() {
        optionAdapter = QuizOptionAdapter(listOf()) { selectedOption ->
            handleAnswer(selectedOption)
        }
        binding.rvOptions.layoutManager = LinearLayoutManager(this)
        binding.rvOptions.adapter = optionAdapter
    }

    private fun showQuestion(index: Int) {
        val question = questions[index]
        
        binding.tvQuestionCount.text = getString(R.string.quiz_question_count, index + 1)
        binding.tvQuestion.text = question.qContent
        
        val options = listOf(
            QuizOption(question.answerA, question.answerA == question.correctAnswer),
            QuizOption(question.answerB, question.answerB == question.correctAnswer),
            QuizOption(question.answerC, question.answerC == question.correctAnswer),
            QuizOption(question.answerD, question.answerD == question.correctAnswer)
        ).shuffled()
        
        optionAdapter.updateOptions(options)
        startTimer()
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(QUIZ_DURATION, 100) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished.toFloat() / QUIZ_DURATION * 100).toInt()
                binding.timerProgress.progress = progress
                binding.tvTimer.text = "${(millisUntilFinished / 1000) + 1}s"
            }

            override fun onFinish() {
                binding.timerProgress.progress = 0
                binding.tvTimer.text = "0s"
                handleAnswer(null) // Time up
            }
        }.start()
    }

    private fun handleAnswer(selectedOption: QuizOption?) {
        timer?.cancel()
        optionAdapter.revealResults()
        
        if (selectedOption?.isCorrect == true) {
            correctCount++
        } else {
            incorrectCount++
        }
        
        updateStats()

        // Delay before next question
        Handler(Looper.getMainLooper()).postDelayed({
            moveToNextQuestion()
        }, 1500)
    }

    private fun updateStats() {
        binding.tvCorrectCount.text = correctCount.toString()
        binding.tvIncorrectCount.text = incorrectCount.toString()
        binding.tvScore.text = getString(R.string.quiz_score, correctCount)
    }


    private fun moveToNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            showQuestion(currentQuestionIndex)
        } else {
            showResults()
        }
    }

    private fun showResults() {
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("SCORE", correctCount)
        intent.putExtra("TOTAL", questions.size)
        intent.putExtra("SUB_LEVEL", getIntent().getIntExtra("SUB_LEVEL", 1))
        startActivity(intent)
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
