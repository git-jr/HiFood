package com.paradoxo.hifood.ui.viewmodel

import androidx.lifecycle.*
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.repository.ProdutoRepository
import kotlinx.coroutines.launch

class ListaProdutosViewModel(
    private val repository: ProdutoRepository
) : ViewModel() {

    private val _produtosLiveData = MutableLiveData<List<Produto>>()
    val produtosLiveData = _produtosLiveData

    suspend fun sincroniza() {
        repository.sincroniza()
    }

    fun buscaTodos() {
        repository.buscaTodos()
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