package com.jgeniselli.banco.game.common.view.player.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.domain.Player

open class PlayerSelectionAdapter : RecyclerView.Adapter<PlayerSelectionViewHolder>() {

    var players: List<Player> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerSelectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.line_view_player_selection, parent, false
        )
        return PlayerSelectionViewHolder(view)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerSelectionViewHolder, position: Int) {
        val player = players[position]
        holder.setTitle(player.name)
        holder.setIconColor(player.colorHex)
    }
}