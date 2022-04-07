package io.github.brunocu.rssotaku.crunchyroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.brunocu.rssotaku.R

class CrunchyAdapter(private val feedEntries: List<CrunchyEntry>) :
    RecyclerView.Adapter<CrunchyAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtPubDate: TextView = view.findViewById(R.id.txtPubDate)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val txtLink: TextView = view.findViewById(R.id.txtLink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cartita, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEntry: CrunchyEntry = feedEntries[position]
        holder.txtTitle.text = currentEntry.title
        holder.txtPubDate.text = currentEntry.pubDate
        holder.txtDescription.text = currentEntry.description
        holder.txtLink.text = "Watch now: " + currentEntry.link
    }

    override fun getItemCount() = feedEntries.size
}