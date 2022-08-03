package com.paradoxo.hifood.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.paradoxo.hifood.R
import com.paradoxo.hifood.dao.ProdutoDAO
import com.paradoxo.hifood.databinding.ActivityListaProdutosBinding
import com.paradoxo.hifood.ui.dialog.FormularioImagemDialog
import com.paradoxo.hifood.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {
    private val dao = ProdutoDAO()
    private val adapter = ListaProdutosAdapter(
        this,
        dao.buscaTodo()
    )

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodo())
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

            val intent: Intent = Intent(this, DetalhesProdutoActivity::class.java)
            intent.putExtra("produto", produto)
            startActivity(intent)
        }
    }

}