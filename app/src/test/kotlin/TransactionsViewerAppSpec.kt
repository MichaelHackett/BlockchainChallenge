package com.blockchain.challenge.app

import com.blockchain.challenge.model.BitcoinTransaction
import io.reactivex.Observable
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeNull
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.Date
import java.util.GregorianCalendar

object TransactionsViewerAppSpec: Spek({
    val dateTimeFormatter = { t: Date ->
        "Y=%d,M=%02d,D=%02d,T=%02d:%02d".format(t.year + 1900, t.month, t.date, t.hours, t.minutes) }
    val targetView = object : TransactionListView {
        var displayedTransactions: List<TransactionView>? = null
        override fun accept(transactionViews: List<TransactionView>) {
            displayedTransactions = transactionViews
        }
    }

    describe("A transaction view") {
        val subject by memoized { TransactionsViewerApp() }

        context("given an empty list of transactions") {
            beforeEach {
                subject.displayTransactions(targetView, dateTimeFormatter)
                        { _ -> Observable.just(listOf()) }
            }
            it("should update the displayed transactions view") {
                targetView.displayedTransactions.shouldNotBeNull()
            }
            it("should display 0 transactions") {
                targetView.displayedTransactions?.shouldBeEmpty()
            }
        }

        context("given a single spending transaction") {
            beforeEach {
                val tx = BitcoinTransaction(-591136, GregorianCalendar(2017, 11, 27, 15, 48).time)
                subject.displayTransactions(targetView, dateTimeFormatter,
                        { _ -> Observable.just(listOf(tx))})
            }
            it("should display 1 transaction") {
                targetView.displayedTransactions?.size.shouldEqual(1)
            }
            describe("the presentation of the transaction") {
                var transactionView: TransactionView? = null
                beforeEach {
                    transactionView = targetView.displayedTransactions?.first()
                }
                it("should display 'spent' message in transaction view") {
                    transactionView?.action.shouldEqual(TransactionAction.SPENT)
                }
                it("should display absolute value of amount, as BTC") {
                    transactionView?.amount.shouldEqual("0.00591136")
                }
                it("should display time from transaction, formatted by supplied date formatter") {
                    transactionView?.time.shouldEqual("Y=2017,M=11,D=27,T=15:48")
                }
            }
        }

        context("given a single receipt transaction") {
            beforeEach {
                val tx = BitcoinTransaction(8537, GregorianCalendar(2020, 5, 1, 0, 7).time)
                subject.displayTransactions(targetView, dateTimeFormatter,
                        { _ -> Observable.just(listOf(tx))})
            }
            it("should display 1 transaction") {
                targetView.displayedTransactions?.size.shouldEqual(1)
            }
            describe("the presentation of the transaction") {
                var transactionView: TransactionView? = null
                beforeEach {
                    transactionView = targetView.displayedTransactions?.first()
                }
                it("should display 'received' message in transaction view") {
                    transactionView?.action.shouldEqual(TransactionAction.RECEIVED)
                }
                it("should display absolute value of amount from transaction, as BTC") {
                    transactionView?.amount.shouldEqual("0.00008537")
                }
                it("should display time from transaction, formatted by supplied date formatter") {
                    transactionView?.time.shouldEqual("Y=2020,M=05,D=01,T=00:07")
                }
            }
        }

        context("given several transactions") {
            beforeEach {
                val transactions = listOf(
                    BitcoinTransaction(8537, GregorianCalendar(2020, 5, 1, 0, 7).time),
                    BitcoinTransaction(-591136, GregorianCalendar(2017, 11, 27, 15, 48).time),
                    BitcoinTransaction(100000, GregorianCalendar(2018, 1, 19, 3, 0).time)
                )
                subject.displayTransactions(targetView, dateTimeFormatter,
                        { _ -> Observable.just(transactions)})
            }
            it("should display all transactions, in order") {
                targetView.displayedTransactions?.size.shouldEqual(3)
                targetView.displayedTransactions?.map { it.amount }
                    .shouldEqual(listOf("0.00008537", "0.00591136", "0.00100000"))
            }
        }
    }
})
