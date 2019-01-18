package com.blockchain.challenge.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blockchain.challenge.R
import com.blockchain.challenge.model.BitcoinAddress
import com.blockchain.challenge.sources.BlockchainService
import com.blockchain.challenge.sources.MultiAddressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val bitcoinAddressHash = "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"

class TransactionsListActivity : AppCompatActivity() {
    val bitcoinAddress = BitcoinAddress(bitcoinAddressHash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_list)

        val transactionsListView = findViewById<RecyclerView>(R.id.transactions_list)
        transactionsListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@TransactionsListActivity)
        }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://blockchain.info")
            .build()
        val blockchain = retrofit.create(BlockchainService::class.java)

        blockchain.transactions(bitcoinAddress).enqueue(object : Callback<MultiAddressResponse> {
            override fun onResponse(call: Call<MultiAddressResponse>, response: Response<MultiAddressResponse>) {
                if (response.isSuccessful) {
                    transactionsListView.adapter = TransactionListAdapter(
                            response.body()?.transactions ?: listOf())
                }
            }
            override fun onFailure(call: Call<MultiAddressResponse>, t: Throwable) {
                // TODO: Report error
                transactionsListView.adapter = TransactionListAdapter(listOf())
            }
        })
    }
}
