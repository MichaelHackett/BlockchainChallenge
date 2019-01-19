package com.blockchain.challenge.model

/**
 * An address for sending or receiving Bitcoin.
 * This is just a stand-in for now (just wraps a string); a real version might include
 * parsing and rending into different formats.
 */
class BitcoinAddress(private val address: String) {
    override fun toString() = address
}
