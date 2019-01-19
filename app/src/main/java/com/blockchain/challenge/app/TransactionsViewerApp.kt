package com.blockchain.challenge.app

import android.annotation.SuppressLint
import com.blockchain.challenge.model.BitcoinAddress
import com.blockchain.challenge.sources.BitcoinTransaction
import io.reactivex.Observable
import java.text.DateFormat
import java.util.*
import kotlin.math.abs

const val bitcoinAddressHash = "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"

interface TransactionListView {
    fun accept(transactionViews: List<TransactionView>)
}

data class TransactionView(
    val action: TransactionAction,
    val amount: String,
    val time: String
)

enum class TransactionAction {
    SPENT, RECEIVED
}

class TransactionsViewerApp {
    private val bitcoinAddress = BitcoinAddress(bitcoinAddressHash)

    @SuppressLint("CheckResult")
    fun displayTransactions(
            view: TransactionListView,
            dateTimeFormatter: (dateTime: Date) -> String,
            transactionsSource: (address: BitcoinAddress) -> Observable<List<BitcoinTransaction>>) {
        transactionsSource(bitcoinAddress).subscribe {
            view.accept(it.map { transaction -> TransactionView(
                    if (transaction.amount < 0) TransactionAction.SPENT else TransactionAction.RECEIVED,
                    "%.8f".format(abs(transaction.amount) / 1e8),
                    dateTimeFormatter(Date(transaction.time * 1000)))
            })}
    }
}
