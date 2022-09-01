package com.paradoxo.hifood.webclient.services

import com.paradoxo.hifood.webclient.model.ProdutoResposta
import retrofit2.Call
import retrofit2.http.GET

interface ProdutoService {

    @GET("produtos.json")
    fun buscaTodos(): Call<List<ProdutoResposta?>>

    @GET("produtos.json")
    suspend fun buscaTodas(): List<ProdutoResposta?>
}