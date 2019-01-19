package com.blockchain.challenge.guiadapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.blockchain.challenge.R
import com.blockchain.challenge.app.TransactionAction
import com.blockchain.challenge.app.TransactionView

class TransactionListAdapter(private val transactionsViews: List<TransactionView>):
        RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val transactionRow: ViewGroup): RecyclerView.ViewHolder(transactionRow)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_list_item, parent, false) as ViewGroup
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val rowGroup = holder.transactionRow
        val transactionView = transactionsViews[position]
        rowGroup.findViewById<TextView>(R.id.transaction_list_item_action_text).setText(
                if (transactionView.action == TransactionAction.SPENT) R.string.spent_label
                else R.string.received_label)
        rowGroup.findViewById<TextView>(R.id.transaction_list_item_amount_text).text = transactionView.amount
        rowGroup.findViewById<TextView>(R.id.transaction_list_item_time_text).text = transactionView.time
    }

    override fun getItemCount() = transactionsViews.size
}
