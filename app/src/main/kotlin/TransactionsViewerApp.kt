package com.blockchain.challenge.app

import com.blockchain.challenge.model.BitcoinAddress
import com.blockchain.challenge.model.BitcoinTransaction
import io.reactivex.Observable
import java.util.Date
import kotlin.math.abs

const val bitcoinAddressHash = "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"

/**
 * Platform-independent application code. This is a very simple app that has only a single
 * function and view: displaying a list of Bitcoin transactions for a hard-coded address.
 */
class TransactionsViewerApp {
    private val bitcoinAddress = BitcoinAddress(bitcoinAddressHash)

    fun displayTransactions(
        view: TransactionListView,
        dateTimeFormatter: (dateTime: Date) -> String,
        transactionsSource: (address: BitcoinAddress) -> Observable<List<BitcoinTransaction>>
    ) {
        transactionsSource(bitcoinAddress).subscribe {
            view.accept(it.map { transaction ->
                TransactionView(
                    if (transaction.satoshis < 0) TransactionAction.SPENT else TransactionAction.RECEIVED,
                    "%.8f".format(abs(transaction.satoshis) / 1e8),
                    dateTimeFormatter(transaction.dateTime)
                )
            })
        }
    }
}

/** Transaction list display port. */
interface TransactionListView {
    fun accept(transactionViews: List<TransactionView>)
}

/** Defines the formatted output for a single transaction in a [TransactionListView]. */
data class TransactionView(
    val action: TransactionAction,
    val amount: String,
    val time: String
)

enum class TransactionAction {
    SPENT, RECEIVED
}
