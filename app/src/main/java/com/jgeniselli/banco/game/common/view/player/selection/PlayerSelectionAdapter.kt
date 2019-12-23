package com.jgeniselli.banco.game.common.view.player.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.game.common.domain.Player

open class PlayerSelectionAdapter : RecyclerView.Adapter<IconAndTitleViewHolder>() {

    var players: List<Player> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconAndTitleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            IconAndTitleViewHolder.viewId, parent, false
        )
        return IconAndTitleViewHolder(view)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: IconAndTitleViewHolder, position: Int) {
        val player = players[position]
        holder.setTitle(player.name)
        holder.setIconColor(player.colorHex)
    }
}