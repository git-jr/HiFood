package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.databinding.ActivityListaProdutosBinding
import com.paradoxo.hifood.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.launch

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {
    private val adapter = ListaProdutosAdapter(this)

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            val produtos = dao.buscaTodos()
            adapter.atualiza(produtos)

        }
    }


    private fun configuraFab() {
        val fab = binding.activityListaProdutosExtendFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
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

}