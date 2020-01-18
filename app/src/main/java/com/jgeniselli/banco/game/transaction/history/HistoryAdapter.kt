package com.jgeniselli.banco.game.transaction.history

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import kotlinx.android.synthetic.main.line_view_transaction_history.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var history = listOf<HistoryRow>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = history.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.line_view_transaction_history, parent, false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = history[position]
        holder.setIconColor(row.iconColorHex)
        holder.setValueColor(row.valueColorHex)
        holder.setValueText(row.formattedValue)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun setIconColor(color: String?) {
            itemView.icon.setColorFilter(getColor(color))
        }

        fun setValueColor(color: String?) {
            itemView.text_value.setTextColor(getColor(color))
        }

        fun setValueText(value: String?) {
            itemView.text_value.text = value
        }

        private fun getColor(hex: String?): Int {
            return hex?.let {
                return try {
                    Color.parseColor(hex)
                } catch (e: Exception) {
                    Color.BLACK
                }
            } ?: Color.BLACK
        }
    }
}