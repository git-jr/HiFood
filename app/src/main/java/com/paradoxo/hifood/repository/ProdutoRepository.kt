package com.paradoxo.hifood.repository

import com.paradoxo.hifood.database.dao.ProdutoDao
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.webclient.ProdutoWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first

class ProdutoRepository(
    private val dao: ProdutoDao,
    private val webClient: ProdutoWebClient
) {
    private val produtosFlow = MutableStateFlow<Flow<List<Produto>>>(emptyFlow())

    fun buscaTodos(): Flow<List<Produto>> {
        return dao.buscaTodos()
    }

    private suspend fun atualizaTodos() {
        webClient.buscaTodos().let { produtos ->
            val produtosSincronizados = produtos.map { produto ->
                produto?.copy(sincronizado = true)
            }
            dao.salva(produtosSincronizados)
        }
    }

    fun buscaTodosDoUsuario(usuarioId: String): Flow<List<Produto>> {
        produtosFlow.value = dao.buscaTodosdDoUsuario(usuarioId)
        return produtosFlow.value
    }

    suspend fun buscaTodosdDoUsuarioSemFlow(usuarioId: String): List<Produto> {
        return dao.buscaTodosdDoUsuarioSemFlow(usuarioId)
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
        val produtosDesativados = dao.buscaDesativado().first()
        produtosDesativados.forEach { produtoDesativado ->
            remove(produtoDesativado.id)
        }
        val produtosNaoSincronizados = dao.buscaNaoSincronizados().first()
        produtosNaoSincronizados.forEach { produtoNaoSincronizado ->
            salva(produtoNaoSincronizado)
        }
        atualizaTodos()
    }

    suspend fun remove(id: String) {
        if (webClient.remove(id)) {
            dao.desativa(id)
        }
    }
}