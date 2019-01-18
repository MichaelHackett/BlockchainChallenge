package com.blockchain.challenge.app

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.blockchain.challenge.R

class TransactionListAdapter :
        RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    private val transactions : List<String> = listOf("A", "B", "C")

    class TransactionViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TransactionListAdapter.TransactionViewHolder {
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_list_item, parent, false) as TextView
        return TransactionViewHolder(textView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.textView.text = transactions[position]
    }

    override fun getItemCount() = transactions.size
}
