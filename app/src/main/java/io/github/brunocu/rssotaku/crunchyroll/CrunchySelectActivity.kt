package io.github.brunocu.rssotaku.crunchyroll

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.github.brunocu.rssotaku.R

const val CRUNCHYROLL_FEED = "io.github.brunocu.rssotaku.CRUNCHYROLL"

class CrunchySelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crunchy_select)
    }

    fun openActivity(view: View) {
        val intent = when (view.id) {
            R.id.btn_recent -> Intent(this, CrunchyrollFeed::class.java).apply {
                putExtra(CRUNCHYROLL_FEED, "RECENT")
            }
            R.id.btn_popular -> Intent(this, CrunchyrollFeed::class.java).apply {
                putExtra(CRUNCHYROLL_FEED, "POPULAR")
            }
            else -> throw IllegalStateException("Invalid button")
        }
        startActivity(intent)
    }
}