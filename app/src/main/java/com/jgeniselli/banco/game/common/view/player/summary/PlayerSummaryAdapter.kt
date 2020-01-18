package com.jgeniselli.banco.game.common.view.player.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.play.PlayerRow

class PlayerSummaryAdapter(
    val onPositionClickListener: (Int) -> Unit
) : RecyclerView.Adapter<PlayerSummaryViewHolder>() {

    var players: List<PlayerRow> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerSummaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.line_view_player_summary, parent, false
        )
        return PlayerSummaryViewHolder(view)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerSummaryViewHolder, position: Int) {
        val player = players[position]
        holder.setPlayerCash(player.formattedCash)
        holder.setIconColor(player.colorHex)
        holder.setClickListener {
            onPositionClickListener(holder.layoutPosition)
        }
    }
}