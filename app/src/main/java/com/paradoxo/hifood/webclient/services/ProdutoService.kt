package com.paradoxo.hifood.webclient.services

import com.paradoxo.hifood.webclient.model.ProdutoRequisicao
import com.paradoxo.hifood.webclient.model.ProdutoResposta
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ProdutoService {

    @GET("produtos.json")
    fun buscaTodos(): Call<List<ProdutoResposta?>>

    @GET("produtos.json")
    suspend fun buscaTodas(): Map<String, ProdutoResposta?>

    @PUT("produtos/{id}.json")
    suspend fun salva(
        @Path("id") id: String,
        @Body produto: ProdutoRequisicao
    ): Response<ProdutoResposta>

    @DELETE("produtos/{id}.json")
    suspend fun remove(@Path("id") id: String): Response<Void>
}