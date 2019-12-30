package com.jgeniselli.banco.game.common.view.color

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import com.jgeniselli.banco.game.common.view.player.selection.IconAndTitleViewHolder
import com.jgeniselli.banco.game.common.view.player.selection.TitleAndColor

open class ColorAdapter : RecyclerView.Adapter<IconAndTitleViewHolder>() {

    private val selectedIndexes = mutableListOf<Int>()

    val selectedRows: List<Int>
        get() = selectedIndexes

    var rows: List<TitleAndColor> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconAndTitleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.line_view_icon_and_title, parent, false
        )
        return IconAndTitleViewHolder(view)
    }

    override fun getItemCount(): Int = rows.size

    override fun onBindViewHolder(holder: IconAndTitleViewHolder, position: Int) {
        val row = rows[position]
        holder.setTitle(row.title)
        holder.setIconColor(row.colorHex)
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

    private fun IconAndTitleViewHolder.setOnSelectionChanged(listener: (Int) -> Unit) {
        itemView.setOnClickListener {
            listener(layoutPosition)
        }
    }
}