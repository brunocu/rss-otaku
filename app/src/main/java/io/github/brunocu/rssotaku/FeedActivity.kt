package io.github.brunocu.rssotaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import io.github.brunocu.rssotaku.helper.FeedParser
import io.github.brunocu.rssotaku.helper.Entry
import io.github.brunocu.rssotaku.helper.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

const val FEED = "io.github.brunocu.rssotaku.FEED"

class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        lifecycleScope.launch {
            val recyclerView = findViewById<RecyclerView>(R.id.crunchy_recycler)
            val feedURL = when (intent.getStringExtra(FEED)) {
                "RECENT" -> getString(R.string.CRUNCHYROLL_RECENT)
                "POPULAR" -> getString(R.string.CRUNCHYROLL_POPULAR)
                "NEWS" -> getString(R.string.ANIME_NEWS_NETWORK)
                "MANGA" -> getString(R.string.KODANSHA)
                else -> throw IllegalStateException("Invalid feed")
            }
            val cardType = when(intent.getStringExtra(FEED)) {
                "RECENT", "POPULAR" -> "CRUNCHYROLL"
                else -> intent.getStringExtra(FEED)
            }
            val downloader = Downloader(recyclerView, cardType!!)
            downloader.loadFeed(feedURL)
        }
    }

    companion object {
        private class Downloader(
            private val recyclerView: RecyclerView,
            private val cardType: String
        ) {
            private val TAG = "FeedDownloader"

            suspend fun loadFeed(url: String) {
                var entries: List<Entry>? = null
                // fill entries asynchronously
                withContext(Dispatchers.IO) {
                    try {
                        val rssFeed = URL(url)
                        val parser = FeedParser()
                        entries = parser.parse(rssFeed.openStream())
                    } catch (e: Exception) {
                        // How to call toast from IO dispatcher ?
                        Log.e(TAG, "loadFeed error: ${e.message}")
                    }
                }
                val adapter = RecyclerAdapter(entries!!, cardType)
                recyclerView.adapter = adapter
                // recyclerView.layoutManager = LinearLayoutManager(context)
            }
        }
    }
}