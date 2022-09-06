package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.databinding.ActivityDetalhesProdutoBinding
import com.paradoxo.hifood.extensions.formataParaMoedaBrasileira
import com.paradoxo.hifood.extensions.tentaCarregarImagem
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.repository.ProdutoRepository
import com.paradoxo.hifood.webclient.ProdutoWebClient
import kotlinx.coroutines.launch

class DetalhesProdutoActivity : AppCompatActivity(R.layout.activity_detalhes_produto) {

    private var produtoId: String? = null
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        ProdutoRepository(
            AppDatabase.instancia(this).produtoDao(),
            ProdutoWebClient()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentarCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    private fun buscaProduto() {
        lifecycleScope.launch {
            produtoId?.let { id ->
                repository.buscaPorId(id).collect { produto ->
                    produto?.let {
                        carregaProdutoNaTela(it)
                    } ?: finish()
                }
            }
        }
    }

    fun tentarCarregarProduto() {
        produtoId = intent.getStringExtra(CHAVE_PRODUTO_ID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(CHAVE_PRODUTO_ID, produtoId)
                    startActivity(this)
                }
            }
            R.id.menu_detalhes_produto_remover -> {
                remove()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun remove() {
        lifecycleScope.launch {
            produtoId?.let { id ->
                repository.remove(id)
            }
            finish()
        }
    }

    private fun carregaProdutoNaTela(produtoRecuperado: Produto) {
        produtoRecuperado.apply {
            binding.apply {
                activityDetalhesProdutoImagem.tentaCarregarImagem(imagem)
                activityDetalhesProdutoTitulo.text = nome
                activityDetalhesProdutoDescricao.text = descricao
                activityDetalhesProdutoValor.text = valor.formataParaMoedaBrasileira()
            }
        }
    }
}