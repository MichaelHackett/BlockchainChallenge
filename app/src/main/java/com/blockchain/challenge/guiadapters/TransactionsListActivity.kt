package com.blockchain.challenge.guiadapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blockchain.challenge.R
import com.blockchain.challenge.app.TransactionListView
import com.blockchain.challenge.app.TransactionView
import com.blockchain.challenge.app.TransactionsViewerApp
import com.blockchain.challenge.model.BitcoinAddress
import com.blockchain.challenge.sources.BitcoinTransaction
import com.blockchain.challenge.sources.blockchainService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.DateFormat

class TransactionsListActivity : AppCompatActivity() {
    private lateinit var app: TransactionsViewerApp

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_list)
        configureRecyclerView(R.id.transactions_list)

        app = TransactionsViewerApp()
        app.displayTransactions(transactionsListViewAdapter(),
            { DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(it) },
            { address -> transactionsSource(address) })
    }

    private fun configureRecyclerView(@IdRes viewId: Int) {
        findViewById<RecyclerView>(viewId).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@TransactionsListActivity)
        }
    }

    private fun transactionsSource(address: BitcoinAddress): Observable<List<BitcoinTransaction>> {
        return blockchainService().transactions(address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map { it.transactions }
    }

    private fun transactionsListViewAdapter() = object : TransactionListView {
        override fun accept(transactionViews: List<TransactionView>) {
            findViewById<RecyclerView>(R.id.transactions_list).adapter =
                    TransactionListAdapter(transactionViews)
        }
    }
}
