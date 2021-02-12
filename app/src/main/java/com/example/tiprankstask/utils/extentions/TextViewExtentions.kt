package com.example.tiprankstask.utils.extentions

import android.text.Html
import android.widget.TextView
import java.util.regex.Matcher
import java.util.regex.Pattern

fun TextView.fromParagraphHTML(descriptionHTML: String) {
    val description: String?
    val p: Pattern = Pattern.compile(
        "<p>(.*)</p>",
        Pattern.DOTALL
    )
    val matcher: Matcher = p.matcher(
        descriptionHTML
    )
    val found = matcher.find()
    description = if (found)
        matcher.group(1)
    else
        descriptionHTML
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(description, 0)
    } else this.text = Html.fromHtml(description)
}