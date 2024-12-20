package com.dicoding.picodiploma.loginwithanimation.view.DetailStory

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postponeEnterTransition()
        
        val story = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY)
        story?.let { showDetail(it) }
    }

       private fun showDetail(story: ListStoryItem) {
        binding.apply {
            // Set transition name yang sesuai dengan ID story
            ivStory.transitionName = "story_image_${story.id}"
            tvName.text = story.name
            tvDescription.text = story.description
            
            Glide.with(this@DetailStoryActivity)
                .load(story.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Mulai transition setelah gambar gagal dimuat
                        startPostponedEnterTransition()
                        return false
                    }
                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Mulai transition setelah gambar berhasil dimuat
                        startPostponedEnterTransition()
                        return false
                    }
                })
                .into(ivStory)
        }
    }
    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}