package com.paradoxo.hifood.webclient.services

import com.paradoxo.hifood.model.Produto
import retrofit2.Call
import retrofit2.http.GET

interface ProdutoService {

    @GET("produtos.json")
    fun buscaTodos(): Call<List<Produto>>
}