package com.blockchain.challenge.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blockchain.challenge.R

class TransactionsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_list)

        findViewById<RecyclerView>(R.id.transactions_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@TransactionsListActivity)
            adapter = TransactionListAdapter()
        }
    }
}
