package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.databinding.ActivityDetalhesProdutoBinding
import com.paradoxo.hifood.extensions.formataParaMoedaBrasileira
import com.paradoxo.hifood.extensions.tentaCarregarImagem
import com.paradoxo.hifood.model.Produto

class DetalhesProdutoActivity : AppCompatActivity(R.layout.activity_detalhes_produto) {

    private var produtoId: Long = 0L
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instancia((this)).produtoDao()
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
        produto = produtoDao.buscaPorId(produtoId)
        produto?.let {
            carregaProdutoNaTela(it)
        } ?: finish()
    }

    fun tentarCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
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
                produto?.let { produtoDao.remove(it) }
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
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