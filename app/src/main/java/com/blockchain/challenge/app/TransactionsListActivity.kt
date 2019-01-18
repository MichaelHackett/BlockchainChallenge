package com.blockchain.challenge.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blockchain.challenge.R
import com.blockchain.challenge.model.BitcoinAddress
import com.blockchain.challenge.sources.BlockchainService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val bitcoinAddressHash = "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"

class TransactionsListActivity : AppCompatActivity() {
    val bitcoinAddress = BitcoinAddress(bitcoinAddressHash)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_list)

        val transactionsListView = findViewById<RecyclerView>(R.id.transactions_list)
        transactionsListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@TransactionsListActivity)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://blockchain.info")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val blockchain = retrofit.create(BlockchainService::class.java)

        blockchain.transactions(bitcoinAddress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe {
            transactionsListView.adapter = TransactionListAdapter(it.transactions)
        }
    }
}
