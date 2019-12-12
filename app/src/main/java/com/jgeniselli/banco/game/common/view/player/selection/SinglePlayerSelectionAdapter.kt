package com.jgeniselli.banco.game.common.view.player.selection

import com.jgeniselli.banco.game.common.domain.Player

class SinglePlayerSelectionAdapter(
    private val indexSelectionListener: (Player) -> Unit
) : PlayerSelectionAdapter() {

    override fun onBindViewHolder(holder: PlayerSelectionViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.setOnClickListener {
            indexSelectionListener(players[position])
        }
    }

    private fun PlayerSelectionViewHolder.setOnClickListener(listener: () -> Unit) =
        itemView.setOnClickListener { listener() }
}
