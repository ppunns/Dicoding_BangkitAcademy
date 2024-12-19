package com.dicoding.picodiploma.loginwithanimation.view.DetailStory

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.bumptech.glide.Glide

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY)
        story?.let { showDetail(it) }
    }

    private fun showDetail(story: ListStoryItem) {
        binding.apply {
            tvName.text = story.name
            tvDescription.text = story.description
            Glide.with(this@DetailStoryActivity)
                .load(story.photoUrl)
                .into(ivStory)
        }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}