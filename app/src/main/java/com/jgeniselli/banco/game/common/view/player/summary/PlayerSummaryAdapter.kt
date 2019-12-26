package com.jgeniselli.banco.game.common.view.player.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.domain.Player

class PlayerSummaryAdapter(
    val onPlayerClickListener: (Player) -> Unit
) : RecyclerView.Adapter<PlayerSummaryViewHolder>() {

    var players: List<Player> = listOf()
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
        holder.setPlayerCash(player.currentValue)
        holder.setIconColor(player.color.colorHex)
        holder.setClickListener {
            onPlayerClickListener(player)
        }
    }
}