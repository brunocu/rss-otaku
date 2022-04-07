package io.github.brunocu.rssotaku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.github.brunocu.rssotaku.crunchyroll.CrunchySelectActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openActivity(view: View) {
        val intent = when (view.id) {
            R.id.btn_crunchyroll -> Intent(this, CrunchySelectActivity::class.java)
            R.id.btn_waifu -> Intent(this, WaifuActivity::class.java)
            R.id.btn_news -> Intent(this, FeedActivity::class.java).apply {
                putExtra(FEED, "NEWS")
            }
            R.id.btn_manga -> Intent(this, FeedActivity::class.java).apply {
                putExtra(FEED, "MANGA")
            }
            else -> throw IllegalStateException("Invalid button")
        }
        startActivity(intent)
    }

}