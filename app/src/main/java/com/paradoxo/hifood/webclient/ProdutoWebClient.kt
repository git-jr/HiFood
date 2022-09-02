package com.paradoxo.hifood.webclient

import android.util.Log
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.webclient.services.ProdutoService

private const val TAG = "ProdutoWebClient"

class ProdutoWebClient {

    private val produtoService: ProdutoService =
        RetrofitInit().produtoService

    suspend fun buscaTodos(): List<Produto?> {
        return try {
            val produtoResposta = produtoService
                .buscaTodas()
            produtoResposta.filterNotNull().map { produtoResposta ->
                produtoResposta?.produto
            }
        } catch (e: Exception) {
            Log.i(TAG, "buscaTodos: ")
            emptyList()
        }
    }

}