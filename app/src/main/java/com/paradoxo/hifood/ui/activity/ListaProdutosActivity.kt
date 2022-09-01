package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.databinding.ActivityListaProdutosBinding
import com.paradoxo.hifood.model.Usuario
import com.paradoxo.hifood.ui.recyclerview.adapter.ListaProdutosAdapter
import com.paradoxo.hifood.webclient.ProdutoWebClient
import com.paradoxo.hifood.webclient.RetrofitInit
import com.paradoxo.hifood.webclient.model.ProdutoResposta
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaProdutosActivity : UsuarioBaseActivity() {

    private val adapter = ListaProdutosAdapter(this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val produtoDAO by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }

    private val webClient by lazy {
        ProdutoWebClient()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

        lifecycleScope.launch {
            val produtos = webClient.buscaTodos()
            Log.i("Lista Produtos", "onCreate: Retrofit Coroutines: $produtos")
            launch {
                usuario
                    .filterNotNull()
                    .collect() { usuario ->
                        buscaProdutosUsuario(usuario.id)
                    }
            }
        }


    }

    private fun retrofitSemCoroutines() {
        val call: Call<List<ProdutoResposta?>> = RetrofitInit().produtoService.buscaTodos()
        lifecycleScope.launch(IO) {
            call.enqueue(object : Callback<List<ProdutoResposta?>?> {
                override fun onResponse(
                    call: Call<List<ProdutoResposta?>?>,
                    respota: Response<List<ProdutoResposta?>?>
                ) {
                    respota.body()?.let { produtosResposta ->
                        val produtos = produtosResposta.map {
                            it?.produto
                        }
                        Log.i("Lista Produtos Firebase", "onCreate: $produtos")
                    }
                }

                override fun onFailure(call: Call<List<ProdutoResposta?>?>, t: Throwable) {
                    Log.i("Lista Produtos Firebase", "onFailure: ", t)
                }
            })
        }
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
        produtoDAO.buscaTodosdDoUsuario(usuarioId).collect { produtos ->
            adapter.atualiza(produtos)
        }
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