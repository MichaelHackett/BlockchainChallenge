package com.blockchain.challenge.model

import java.util.Date

/**
 * Records an individual blockchain transaction.
 * Currently, this is just a shell of what a real model object would be, carrying just the
 * transaction amount and the date/time at which it was recorded.
 */
data class BitcoinTransaction(
    val satoshis: Long,
    val dateTime: Date
)
