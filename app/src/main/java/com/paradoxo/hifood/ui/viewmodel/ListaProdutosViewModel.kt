package com.paradoxo.hifood.ui.viewmodel

import androidx.lifecycle.*
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.repository.ProdutoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListaProdutosViewModel(
    private val repository: ProdutoRepository
) : ViewModel() {

    private val _produtosLiveData = MutableLiveData<List<Produto>>()
    val produtosLiveData = _produtosLiveData

    private val _produtosFlow = MutableStateFlow<List<Produto>>(emptyList())
    val produtosFlow: StateFlow<List<Produto>> = _produtosFlow

    suspend fun sincroniza() {
        repository.sincroniza()
    }

    fun buscaTodos() {
        repository.buscaTodos()
    }

    fun buscaTodosDoUsuarioSoFlow(usuarioId: String): Flow<List<Produto>> {
        viewModelScope.launch {
            repository.buscaTodosDoUsuario(usuarioId).collect {
                _produtosFlow.value = it
            }
        }
        return produtosFlow
    }

    fun buscaTodosDoUsuarioLiveData(usuarioId: String): LiveData<List<Produto>> {
        viewModelScope.launch {
            _produtosLiveData.postValue(repository.buscaTodosdDoUsuarioSemFlow(usuarioId))
        }
        return produtosLiveData
    }

    fun buscaTodosDoUsuarioFlowAsFlow(usuarioId: String): LiveData<List<Produto>> {
        return repository.buscaTodosDoUsuario(usuarioId).asLiveData()
    }

}