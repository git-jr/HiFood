package com.paradoxo.hifood.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.repository.ProdutoRepository
import kotlinx.coroutines.flow.Flow

class ListaProdutosViewModel(
    private val repository: ProdutoRepository
) : ViewModel() {

    suspend fun sincroniza() {
        repository.sincroniza()
    }

    fun buscaTodos() {
        repository.buscaTodos()

    }

    fun buscaTodosdDoUsuario(usuarioId: String): Flow<List<Produto>> {
        return repository.buscaTodosdDoUsuario(usuarioId)
    }
}