package com.blockchain.challenge.sources

import com.blockchain.challenge.model.BitcoinAddress
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockchainService {
    @GET("multiaddr")
    fun transactions(
            @Query("active") address : BitcoinAddress
    ): Call<MultiAddressResponse> // Call -> Observable ?
}

data class MultiAddressResponse(
    @SerializedName("txs") val transactions : List<BitcoinTransaction>
)

data class BitcoinTransaction(
    @SerializedName("result") val amount : Int,
    val time : Int // TODO: decode to time
)
