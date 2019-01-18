package com.blockchain.challenge.app

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.blockchain.challenge.R
import com.blockchain.challenge.sources.BitcoinTransaction

class TransactionListAdapter(private val transactions : List<BitcoinTransaction>) :
        RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TransactionListAdapter.TransactionViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_list_item, parent, false) as TextView
        return TransactionViewHolder(textView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.textView.text = transactions[position].amount.toString()
    }

    override fun getItemCount() = transactions.size
}
