package com.blockchain.challenge.sources

import com.blockchain.challenge.model.BitcoinAddress
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockchainService {
    @GET("multiaddr")
    fun transactions(
            @Query("active") address : BitcoinAddress
    ): Observable<MultiAddressResponse>
}

data class MultiAddressResponse(
    @SerializedName("txs") val transactions : List<BitcoinTransaction>
)

data class BitcoinTransaction(
    @SerializedName("result") val amount : Long,
    val time : Long
)
