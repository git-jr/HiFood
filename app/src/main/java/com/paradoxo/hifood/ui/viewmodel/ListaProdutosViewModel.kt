package com.paradoxo.hifood.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.repository.ProdutoRepository

class ListaProdutosViewModel(
    private val repository: ProdutoRepository
) : ViewModel() {

    // Descbrir como fazer o cache correto aqui:
    // private val produtosLiveData: LiveData<List<Produto>>

    suspend fun sincroniza() {
        repository.sincroniza()
    }

    fun buscaTodos() {
        repository.buscaTodos()
    }

    fun buscaTodosDoUsuario(usuarioId: String): LiveData<List<Produto>> {
        return repository.buscaTodosDoUsuario(usuarioId).asLiveData()
    }

}