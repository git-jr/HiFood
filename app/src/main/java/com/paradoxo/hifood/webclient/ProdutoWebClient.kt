package com.paradoxo.hifood.webclient

import com.paradoxo.hifood.model.Produto

class ProdutoWebClient {

    suspend fun buscaTodos(): List<Produto?> {
        val produtoResposta = RetrofitInit().produtoService
            .buscaTodas()
        return produtoResposta.map { produtoResposta ->
            produtoResposta?.produto
        }
    }
}