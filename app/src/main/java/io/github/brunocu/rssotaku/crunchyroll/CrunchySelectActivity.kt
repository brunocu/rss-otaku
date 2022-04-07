package io.github.brunocu.rssotaku.crunchyroll

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.github.brunocu.rssotaku.FEED
import io.github.brunocu.rssotaku.FeedActivity
import io.github.brunocu.rssotaku.R

class CrunchySelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crunchy_select)
    }

    fun openActivity(view: View) {
        val intent = when (view.id) {
            R.id.btn_recent -> Intent(this, FeedActivity::class.java).apply {
                putExtra(FEED, "RECENT")
            }
            R.id.btn_popular -> Intent(this, FeedActivity::class.java).apply {
                putExtra(FEED, "POPULAR")
            }
            else -> throw IllegalStateException("Invalid button")
        }
        startActivity(intent)
    }
}