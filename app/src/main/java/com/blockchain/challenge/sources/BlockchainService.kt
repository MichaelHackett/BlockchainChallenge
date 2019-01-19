package com.blockchain.challenge.sources

import com.blockchain.challenge.model.BitcoinAddress
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit source for accessing the Blockchain REST API

fun blockchainService(): BlockchainService =
        Retrofit.Builder()
        .baseUrl("https://blockchain.info")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BlockchainService::class.java)

interface BlockchainService {
    @GET("multiaddr")
    fun transactions(
            @Query("active") address: BitcoinAddress
    ): Observable<MultiAddressResponse>
}

data class MultiAddressResponse(
    @SerializedName("txs") val transactions: List<BitcoinTransaction>
)

data class BitcoinTransaction(
    @SerializedName("result") val amount: Long,
    val time: Long
)
