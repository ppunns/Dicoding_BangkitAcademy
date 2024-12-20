package com.dicoding.picodiploma.loginwithanimation.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import com.dicoding.picodiploma.loginwithanimation.R
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.adapter.StoryAdapter
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.AddStory.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.DetailStory.DetailStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                viewModel.getStories(user.token)
            }
        }

        setupView()
        setupAction()
        setupRecyclerView()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
        
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        viewModel.stories.observe(this) { response ->
            response.listStory?.let { stories ->
                binding.rvStories.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = StoryAdapter(stories) { story ->
                        Intent(this@MainActivity, DetailStoryActivity::class.java).also { intent ->
                            intent.putExtra(DetailStoryActivity.EXTRA_STORY, story)
                            val imageView = (binding.rvStories.findViewHolderForAdapterPosition(
                                stories.indexOf(story)
                            )?.itemView?.findViewById<ImageView>(R.id.iv_story))
                            
                            imageView?.let {
                                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    this@MainActivity,
                                    it,
                                    ViewCompat.getTransitionName(it) ?: story.photoUrl ?: "story_image"
                                )
                                startActivity(intent, options.toBundle())
                            } ?: startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        val logout = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(logout)
            startDelay = 100
        }.start()
    }
}