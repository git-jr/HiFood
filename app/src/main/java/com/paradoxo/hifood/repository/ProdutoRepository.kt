package com.paradoxo.hifood.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paradoxo.hifood.database.dao.ProdutoDao
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.webclient.ProdutoWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ProdutoRepository(
    private val dao: ProdutoDao,
    private val webClient: ProdutoWebClient
) {
    private val produtosLiveData = MutableLiveData<Flow<List<Produto>>>()

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

    fun buscaTodosDoUsuario(usuarioId: String): LiveData<Flow<List<Produto>>> {
        produtosLiveData.value = dao.buscaTodosdDoUsuario(usuarioId)
        return produtosLiveData

        // Anotação: Acho que é melhor retornar um LiveData<List<Produto>>
        // e usar o collect do flow aqui dentro
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