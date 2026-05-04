package com.example.advanceenglishdictionary.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.advanceenglishdictionary.fragments.ProverbsFragment
import com.example.advanceenglishdictionary.databinding.ActivityMainBinding
import com.example.advanceenglishdictionary.fragments.IVerbFragment
import com.example.advanceenglishdictionary.fragments.VocaWordFragment

/**
 * MainActivity is the entry point of the app.
 * It sets up ViewBinding, attaches the HomeFragment, and manages UI components.
 */
class MainActivity : AppCompatActivity() {

    // ViewBinding for activity_main.xml
    private lateinit var binding: ActivityMainBinding

    // ==========================
    // Activity Lifecycle
    // ==========================

    /**
     * Called when the activity is starting.
     * Sets up view binding and initializes the default fragment.
     *
     * @param savedInstanceState Bundle containing the activity's previous state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setupViewBinding()
        setContentView(binding.root)

        attachIVerbFragmentIfNeeded(savedInstanceState)
    }

    // ==========================
    // Private Helper Methods
    // ==========================

    /**
     * Initializes ViewBinding for this activity.
     */
    private fun setupViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    /**
     * Attaches the Fragment to the fragment container if it's not already added.
     *
     * @param savedInstanceState Bundle from activity creation, used to prevent duplicate fragments.
     */
    private fun attachIVerbFragmentIfNeeded(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, IVerbFragment())
                .commit()
        }
    }
}