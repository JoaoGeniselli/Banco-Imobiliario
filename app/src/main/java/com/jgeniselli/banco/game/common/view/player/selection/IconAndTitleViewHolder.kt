package com.jgeniselli.banco.game.common.view.player.selection

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import kotlinx.android.synthetic.main.line_view_icon_and_title.view.*

class IconAndTitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        const val viewId = R.layout.line_view_icon_and_title
    }

    var isHighlighted: Boolean = false
        set(value) {
            changeHighlightState(value)
        }

    private fun changeHighlightState(highlight: Boolean) {
        if (highlight) {
            itemView.setBackgroundResource(R.color.lightGray)
        } else {
            itemView.setBackgroundResource(android.R.color.white)
        }
    }

    fun setTitle(title: CharSequence?) {
        itemView.text_title.text = title
    }

    fun setIconColor(colorHex: String?) {
        colorHex?.let {
            try {
                val color = Color.parseColor(colorHex)
                itemView.image_view_icon.setColorFilter(color)
            } catch (e: Exception) {
                itemView.image_view_icon.setColorFilter(R.color.colorAccent)
            }
        }
    }
}