package com.paradoxo.hifood.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun buscaTodosdDoUsuario(usuarioId: String): LiveData<Flow<List<Produto>>> {
        return repository.buscaTodosDoUsuario(usuarioId)

        // Anotação: Acho que é melhor retornar um LiveData<List<Produto>>
        // e usar o collect do flow aqui dentro
    }

//    fun buscaTodosdDoUsuario(usuarioId: String): Flow<List<Produto>> {
//        return repository.buscaTodosdDoUsuario(usuarioId)
//    }
}