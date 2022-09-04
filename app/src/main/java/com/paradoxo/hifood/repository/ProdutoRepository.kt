package com.paradoxo.hifood.repository

import android.util.Log
import com.paradoxo.hifood.database.dao.ProdutoDao
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.webclient.ProdutoWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlin.math.sin

class ProdutoRepository(
    private val dao: ProdutoDao,
    private val webClient: ProdutoWebClient
) {

    fun buscaTodos(): Flow<List<Produto>> {
        return dao.buscaTodos()
    }

    private suspend fun atualizaTodos() {
        webClient.buscaTodos().let { produtos ->
            Log.i("webClient.buscaTodos", "atualizaTodos: $produtos")
            val produtosSincronizados = produtos.map { produto ->
                produto?.copy(sincronizado = true)
            }
            dao.salva(produtosSincronizados)
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
        if (webClient.salva(produto)) {
            val produtoSincronizado = produto.copy(sincronizado = true)
            dao.salva(produtoSincronizado)
        }
    }

    suspend fun sincroniza() {
        val produtosNaoSincronizadas = dao.buscaNaoSincronizados().first()
        produtosNaoSincronizadas.forEach { produtoNaoSincronizado ->
            salva(produtoNaoSincronizado)
        }
        atualizaTodos()
    }
}