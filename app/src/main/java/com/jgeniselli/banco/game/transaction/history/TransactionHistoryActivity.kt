package com.jgeniselli.banco.game.transaction.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgeniselli.banco.R
import kotlinx.android.synthetic.main.activity_transaction_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionHistoryActivity : AppCompatActivity() {

    private val viewModel by viewModel<TransactionHistoryViewModel>()
    private val adapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.historyLiveData.observe(this, Observer { displayHistory(it) })
        lifecycle.addObserver(viewModel)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        recycler_view.adapter = adapter
    }

    private fun displayHistory(history: List<HistoryRow>) {
        adapter.history = history
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
