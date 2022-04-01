package io.github.brunocu.rssotaku.crunchyroll

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.brunocu.rssotaku.CRUNCHYROLL_FEED
import io.github.brunocu.rssotaku.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

data class CrunchyEntry(
    val title: String?,
    val description: String?,
    val pubDate: String?
)

class CrunchyrollFeed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crunchyroll_feed)

        lifecycleScope.launch {
            val recyclerView = findViewById<RecyclerView>(R.id.crunchy_recycler)
            val feedURL = when (intent.getStringExtra(CRUNCHYROLL_FEED)) {
                "RECENT" -> getString(R.string.CRUNCHYROLL_RECENT)
                "POPULAR" -> getString(R.string.CRUNCHYROLL_POPULAR)
                else -> throw IllegalArgumentException("Invalid feed")
            }
            val downloader = Downloader(this@CrunchyrollFeed, recyclerView)
            downloader.loadFeed(feedURL)
        }
    }

    companion object {
        private class Downloader(
            private val context: Context,
            private val recyclerView: RecyclerView
        ) {
            private var TAG = "CrunchyrollFeedDownloader"

            suspend fun loadFeed(url: String) {
                var entries: List<CrunchyEntry>? = null
                // fill entries asynchronously
                withContext(Dispatchers.IO) {
                    try {
                        val rssFeed = URL(url)
                        val parser = CrunchyrollParser()
                        entries = parser.parse(rssFeed.openStream())
                    } catch (e: Exception) {
                        // How to call toast from IO dispatcher
                        Log.e(TAG, "loadFeed error: ${e.message}")
                    }
                }
                val adapter = CrunchyAdapter(entries!!)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
            }
        }
    }
}