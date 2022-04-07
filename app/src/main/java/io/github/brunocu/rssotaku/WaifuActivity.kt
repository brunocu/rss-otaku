package io.github.brunocu.rssotaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

class WaifuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waifu)

        getWaifu(null)
    }

    fun getWaifu(view: View?) {
        val imageView: ImageView = findViewById(R.id.imgWaifu)
        val randomIdx = (0..99999).random()
        Picasso.get().load("https://www.thiswaifudoesnotexist.net/example-${randomIdx}.jpg")
            .into(imageView)
    }
}