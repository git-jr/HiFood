package com.paradoxo.hifood.repository

import android.util.Log
import com.paradoxo.hifood.database.dao.ProdutoDao
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.webclient.ProdutoWebClient
import kotlinx.coroutines.flow.Flow

class ProdutoRepository(
    private val dao: ProdutoDao,
    private val webClient: ProdutoWebClient
) {

    fun buscaTodos(): Flow<List<Produto>> {
        return dao.buscaTodos()
    }

    suspend fun atualizaTodos() {
        webClient.buscaTodos().let { produtos ->
            Log.i("webClient.buscaTodos", "atualizaTodos: $produtos")
            dao.salva(produtos)
        }
    }

    fun buscaTodosdDoUsuario(usuarioId: String): Flow<List<Produto>> {
        return dao.buscaTodosdDoUsuario(usuarioId)
    }

    fun buscaPorId(id: String): Flow<Produto?> {
        return dao.buscaPorId(id)

    }

    suspend fun salva(produto: Produto) {
        dao.salva(produto)
        webClient.salva(produto)
    }
}