package com.paradoxo.hifood.webclient

import android.util.Log
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.webclient.model.ProdutoRequisicao
import com.paradoxo.hifood.webclient.services.ProdutoService

private const val TAG = "ProdutoWebClient"

class ProdutoWebClient {

    private val produtoService: ProdutoService =
        RetrofitInit().produtoService

    suspend fun buscaTodos(): List<Produto?> {

        return try {
            val produtosResposta = produtoService
                .buscaTodas()

            produtosResposta.map { produtoResposta ->
                produtoResposta.value?.produto?.copy(id = produtoResposta.key)
            }

        } catch (e: Exception) {
            Log.i(TAG, "Erro em buscaTodos: ", e)
            emptyList()
        }
    }

    suspend fun salva(produto: Produto): Boolean {
        try {
            val resposta = produtoService.salva(
                produto.id, ProdutoRequisicao(
                    nome = produto.nome,
                    descricao = produto.descricao,
                    valor = produto.valor.toString(),
                    imagem = produto.imagem,
                    usuarioId = produto.usuarioId
                )
            )
            return (resposta.isSuccessful)
        } catch (e: Exception) {
            Log.e(TAG, "salva: falha ao tentar salvar", e)
        }
        return false
    }

    suspend fun remove(id: String): Boolean {
        try {
            produtoService.remove(id)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "salva: falha ao tentar deletar", e)
        }
        return false
    }

}