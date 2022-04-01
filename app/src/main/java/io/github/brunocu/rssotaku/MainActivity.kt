package io.github.brunocu.rssotaku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.github.brunocu.rssotaku.crunchyroll.CrunchyrollFeed

const val CRUNCHYROLL_FEED = "io.github.brunocu.rssotaku.CRUNCHYROLL"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openFeed(view: View) {
        val intent = when(view.id) {
            R.id.btn_recent -> Intent(this, CrunchyrollFeed::class.java).apply {
                putExtra(CRUNCHYROLL_FEED, "RECENT")
            }
            R.id.btn_popular -> Intent(this, CrunchyrollFeed::class.java).apply {
                putExtra(CRUNCHYROLL_FEED, "POPULAR")
            }
            else -> throw IllegalArgumentException("Invalid button")
        }
        startActivity(intent)
    }

}