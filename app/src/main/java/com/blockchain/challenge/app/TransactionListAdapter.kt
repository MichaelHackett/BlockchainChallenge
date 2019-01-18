package com.blockchain.challenge.app

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.blockchain.challenge.R
import com.blockchain.challenge.sources.BitcoinTransaction
import java.text.DateFormat
import java.util.Date
import kotlin.math.abs

class TransactionListAdapter(private val transactions : List<BitcoinTransaction>) :
        RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(val transactionRow: ViewGroup) : RecyclerView.ViewHolder(transactionRow)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TransactionListAdapter.TransactionViewHolder {
        return TransactionViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_list_item, parent, false) as ViewGroup)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val rowGroup = holder.transactionRow
        val txAmount = transactions[position].amount
        val time = Date(transactions[position].time * 1000)
        rowGroup.findViewById<TextView>(R.id.transaction_list_item_action_text).setText(
                if (txAmount < 0) R.string.spent_label else R.string.received_label)
        rowGroup.findViewById<TextView>(R.id.transaction_list_item_amount_text).text =
                "%.8f".format(abs(txAmount) / 1e8)
        rowGroup.findViewById<TextView>(R.id.transaction_list_item_time_text).text =
                DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(time)
    }

    override fun getItemCount() = transactions.size
}
