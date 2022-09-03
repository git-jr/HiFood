package com.paradoxo.hifood.webclient.model

import com.paradoxo.hifood.model.Produto
import java.math.BigDecimal
import java.util.*


class ProdutoResposta(
    val id: String?,
    val nome: String?,
    val descricao: String?,
    val valor: String?,
    val imagem: String?,
    val usuarioId: String?
) {

    val produto: Produto
        get() = Produto(
            id = id ?: UUID.randomUUID().toString(),
            nome = nome ?: "",
            descricao = descricao ?: "",
            valor = BigDecimal(valor.toString()),
            imagem = imagem ?: "",
            usuarioId = usuarioId ?: ""
        )

}
