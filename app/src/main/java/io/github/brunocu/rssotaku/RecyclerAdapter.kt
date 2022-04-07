package io.github.brunocu.rssotaku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Entry(
    val title: String?,
    val description: String?,
    val pubDate: String?,
    val link: String?
)

class RecyclerAdapter(private val feedEntries: List<Entry>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
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
        val currentEntry: Entry = feedEntries[position]
        holder.txtTitle.text = currentEntry.title
        holder.txtPubDate.text = currentEntry.pubDate
        holder.txtDescription.text = currentEntry.description
        holder.txtLink.text = "Watch now: " + currentEntry.link
    }

    override fun getItemCount() = feedEntries.size
}