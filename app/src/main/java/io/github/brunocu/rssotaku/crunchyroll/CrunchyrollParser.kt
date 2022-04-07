package io.github.brunocu.rssotaku.crunchyroll

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

class CrunchyrollParser {
    fun parse(inputSteam: InputStream): List<CrunchyEntry> {
        inputSteam.use { inputSteam ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false) // ?
            parser.setInput(inputSteam, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    private fun readFeed(parser: XmlPullParser): List<CrunchyEntry> {
        val entries = mutableListOf<CrunchyEntry>()

        movetoTag(parser, "channel")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG)
                continue
            if (parser.name == "item")
                entries.add(readItem(parser))
            else
                skip(parser)
        }
        return entries
    }

    private fun movetoTag(parser: XmlPullParser, name: String) {
        parser.require(XmlPullParser.START_TAG, null, "rss")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG)
                continue
            if (parser.name == name)
                break
            else
                skip(parser)
        }
    }

    private fun readItem(parser: XmlPullParser): CrunchyEntry {
        parser.require(XmlPullParser.START_TAG, null, "item")
        var title: String? = null
        var description: String? = null
        var pubDate: String? = null
        var link: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG)
                continue
            when (parser.name) {
                "title" -> title = readText(parser)
                "description" -> description = readDescription(parser)
                "pubDate" -> pubDate = readText(parser)
                "link" -> link = readText(parser)
                else -> skip(parser)
            }
        }
        return CrunchyEntry(title, description, pubDate, link)
    }

    private fun readDescription(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, null, "description")
        val description = readText(parser)
        val regex = Regex("<br />")
        return regex.split(description).last()
    }

    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG)
            throw IllegalStateException()
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}