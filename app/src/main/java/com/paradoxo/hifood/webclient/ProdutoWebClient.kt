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

    suspend fun salva(produto: Produto) {
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
            if (resposta.isSuccessful) {
                Log.i(TAG, "salva: produto salvo com sucesso")
            } else {
                Log.i(TAG, "salva: produto não foi salvo")
            }
        } catch (e: Exception) {
            Log.e(TAG, "salva: falha ao tentar salvar", e)
        }
    }

}