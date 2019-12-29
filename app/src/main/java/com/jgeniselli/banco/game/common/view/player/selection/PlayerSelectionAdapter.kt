package com.jgeniselli.banco.game.common.view.player.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.game.common.domain.Player

open class PlayerSelectionAdapter(
    private val indexSelectionListener: (Int) -> Unit
) : RecyclerView.Adapter<IconAndTitleViewHolder>() {

    var rows: List<TitleAndColor> = listOf()
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

    override fun getItemCount(): Int = rows.size

    override fun onBindViewHolder(holder: IconAndTitleViewHolder, position: Int) {
        val row = rows[position]
        holder.setTitle(row.title)
        holder.setIconColor(row.colorHex)
        holder.setOnClickListener {
            indexSelectionListener(position)
        }
    }

    private fun IconAndTitleViewHolder.setOnClickListener(listener: () -> Unit) =
        itemView.setOnClickListener { listener() }
}

data class TitleAndColor(val title: String, val colorHex: String)