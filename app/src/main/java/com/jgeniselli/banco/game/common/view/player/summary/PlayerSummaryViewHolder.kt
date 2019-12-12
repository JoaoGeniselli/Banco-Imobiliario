package com.jgeniselli.banco.game.common.view.player.summary

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import kotlinx.android.synthetic.main.line_view_player_summary.view.*
import java.text.NumberFormat

class PlayerSummaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun setPlayerCash(cash: Double) {
        val formatter = NumberFormat.getCurrencyInstance()
        val formattedCash = formatter.format(cash)
        itemView.text_player_cash.text = formattedCash
    }

    fun setIconColor(colorHex: String?) {
        colorHex?.let {
            try {
                val color = Color.parseColor(colorHex)
                itemView.image_view.setColorFilter(color)
            } catch (e: Exception) {
                itemView.image_view.setColorFilter(R.color.colorAccent)
            }
        }
    }

    fun setClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}