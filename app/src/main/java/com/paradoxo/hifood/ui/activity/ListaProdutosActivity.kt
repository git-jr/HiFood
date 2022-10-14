package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.databinding.ActivityListaProdutosBinding
import com.paradoxo.hifood.extensions.vaiPara
import com.paradoxo.hifood.repository.ProdutoRepository
import com.paradoxo.hifood.ui.recyclerview.adapter.ListaProdutosAdapter
import com.paradoxo.hifood.ui.viewmodel.ListaProdutosViewModel
import com.paradoxo.hifood.ui.viewmodel.factory.ListaProdutosViewModelFactory
import com.paradoxo.hifood.webclient.ProdutoWebClient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ListaProdutosActivity : UsuarioBaseActivity() {

    private val adapter = ListaProdutosAdapter(this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        val repository = ProdutoRepository(
            AppDatabase.instancia(this).produtoDao(),
            ProdutoWebClient()
        )
        val factory = ListaProdutosViewModelFactory(repository)
        val provedor = ViewModelProvider(
            this, factory = factory
        )
        provedor.get(ListaProdutosViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

        vaiPara(ListaProdutosComposeActivity::class.java)

        lifecycleScope.launch {
            launch {
                sincroniza()
            }

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.buscaTodos()
                usuario
                    .filterNotNull()
                    .collect() { usuario ->
                        buscaProdutosUsuario(usuario.id)
                    }
            }
        }
    }

    private suspend fun sincroniza() {
        viewModel.sincroniza()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_lista_produtos_sair_do_app -> {
                lifecycleScope.launch {
                    deslogaUsuario()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private suspend fun buscaProdutosUsuario(usuarioId: String) {
        viewModel.buscaTodosDoUsuarioSoFlow(usuarioId).collect { produtos ->
            Log.i("teste", "Atualizando produtos Flow")
            adapter.atualiza(produtos)
        }
//
//        viewModel.buscaTodosDoUsuarioLiveData(usuarioId).observe(this,
//            Observer { produtos ->
//                lifecycleScope.launch {
//                    Log.i("teste", "Atualizando produtos livedata")
//                    adapter.atualiza(produtos)
//                }
//            })
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItemLister = { produto ->
            Log.i("ListaProdutosActivityr", "Clicando no item")

            val intent = Intent(this, DetalhesProdutoActivity::class.java)
            intent.putExtra(CHAVE_PRODUTO_ID, produto.id)
            startActivity(intent)
        }
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosExtendFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

}