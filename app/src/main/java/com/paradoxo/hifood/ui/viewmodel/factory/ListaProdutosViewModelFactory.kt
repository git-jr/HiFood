package com.paradoxo.hifood.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paradoxo.hifood.repository.ProdutoRepository
import com.paradoxo.hifood.ui.viewmodel.ListaProdutosViewModel

class ListaProdutosViewModelFactory(
    private val repositorio: ProdutoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListaProdutosViewModel(repositorio) as T
    }
}