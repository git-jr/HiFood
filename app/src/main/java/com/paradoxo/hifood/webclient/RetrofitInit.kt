package com.paradoxo.hifood.webclient

import com.paradoxo.hifood.webclient.services.ProdutoService
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInit {
    private val retrofit = Retrofit.Builder()
        .baseUrl(FIREBASE_URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val produtoService = retrofit.create(ProdutoService::class.java)

}