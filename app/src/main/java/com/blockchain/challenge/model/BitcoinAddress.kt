package com.blockchain.challenge.model

// Stand-in for a real address class; just a string for now.
class BitcoinAddress(private val address: String) {
    override fun toString() = address
}
