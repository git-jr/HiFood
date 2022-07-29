package com.paradoxo.hifood.dao

import com.paradoxo.hifood.model.Produto

class ProdutoDAO {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodo(): List<Produto>{
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>()
    }
}