package io.github.brunocu.rssotaku.crunchyroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import io.github.brunocu.rssotaku.CRUNCHYROLL_FEED
import io.github.brunocu.rssotaku.Entry
import io.github.brunocu.rssotaku.R
import io.github.brunocu.rssotaku.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class CrunchyrollFeed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        lifecycleScope.launch {
            val recyclerView = findViewById<RecyclerView>(R.id.crunchy_recycler)
            val feedURL = when (intent.getStringExtra(CRUNCHYROLL_FEED)) {
                "RECENT" -> getString(R.string.CRUNCHYROLL_RECENT)
                "POPULAR" -> getString(R.string.CRUNCHYROLL_POPULAR)
                else -> throw IllegalStateException("Invalid feed")
            }
            val downloader = Downloader(recyclerView)
            downloader.loadFeed(feedURL)
        }
    }

    companion object {
        private class Downloader(
            private val recyclerView: RecyclerView
        ) {
            private var _TAG = "CrunchyrollFeedDownloader"

            suspend fun loadFeed(url: String) {
                var entries: List<Entry>? = null
                // fill entries asynchronously
                withContext(Dispatchers.IO) {
                    try {
                        val rssFeed = URL(url)
                        val parser = CrunchyrollParser()
                        entries = parser.parse(rssFeed.openStream())
                    } catch (e: Exception) {
                        // How to call toast from IO dispatcher ?
                        Log.e(_TAG, "loadFeed error: ${e.message}")
                    }
                }
                val adapter = RecyclerAdapter(entries!!)
                recyclerView.adapter = adapter
//                recyclerView.layoutManager = LinearLayoutManager(context)
            }
        }
    }
}