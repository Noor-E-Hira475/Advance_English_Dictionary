package com.example.advanceenglishdictionary.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.advanceenglishdictionary.R
import com.example.advanceenglishdictionary.databinding.ActivityQuizResultBinding

class QuizResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 10)
        val subLevel = intent.getIntExtra("SUB_LEVEL", 1)

        binding.tvFinalScore.text = getString(R.string.quiz_your_score, score, total)

        // Set message based on performance
        val percentage = (score.toFloat() / total * 100).toInt()
        when {
            percentage >= 80 -> {
                binding.tvResultStatus.text = getString(R.string.quiz_excellent)
                binding.tvPerformanceMsg.text = getString(R.string.quiz_excellent_msg)
                binding.ivResultImage.setImageResource(R.drawable.ic_quiz_correct)
            }
            percentage >= 50 -> {
                binding.tvResultStatus.text = getString(R.string.quiz_good_job)
                binding.tvPerformanceMsg.text = getString(R.string.quiz_good_job_msg)
                binding.ivResultImage.setImageResource(R.drawable.ic_quiz_correct)
            }
            else -> {
                binding.tvResultStatus.text = getString(R.string.quiz_keep_trying)
                binding.tvPerformanceMsg.text = getString(R.string.quiz_keep_trying_msg)
                binding.ivResultImage.setImageResource(R.drawable.ic_quiz_incorrect)
            }
        }


        binding.btnPlayAgain.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("SUB_LEVEL", subLevel)
            startActivity(intent)
            finish()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }
    }
}
