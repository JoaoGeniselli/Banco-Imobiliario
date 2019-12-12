package com.jgeniselli.banco.game.common.view.player.selection

import com.jgeniselli.banco.game.common.domain.Player

class MultiplePlayerSelectionAdapter : PlayerSelectionAdapter() {

    private val selectedIndexes = mutableListOf<Int>()

    override fun onBindViewHolder(holder: PlayerSelectionViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.setOnSelectionChanged { updatedIndex ->
            val itWasSelected = selectedIndexes.contains(updatedIndex)
            if (itWasSelected) {
                selectedIndexes.remove(updatedIndex)
                holder.isHighlighted = false
            } else {
                selectedIndexes.add(updatedIndex)
                holder.isHighlighted = true
            }
        }
    }

    fun getSelectedPlayers(): List<Player> {
        return players.filterIndexed { index, _ ->
            selectedIndexes.contains(index)
        }
    }

    private fun PlayerSelectionViewHolder.setOnSelectionChanged(listener: (Int) -> Unit) {
        itemView.setOnClickListener {
            listener(layoutPosition)
        }
    }
}