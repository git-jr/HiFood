package com.paradoxo.hifood.dao

import com.paradoxo.hifood.model.Produto
import java.math.BigDecimal

class ProdutoDAO {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodo(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
                nome = "Salada de Frutas",
                descricao = "Laranja, Uva e BlueBerry",
                valor = BigDecimal("99.99")
            )
        )
    }
}