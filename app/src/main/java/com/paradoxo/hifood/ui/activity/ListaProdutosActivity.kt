package com.paradoxo.hifood.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.paradoxo.hifood.R
import com.paradoxo.hifood.dao.ProdutoDAO
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.databinding.ActivityListaProdutosBinding
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.ui.dialog.FormularioImagemDialog
import com.paradoxo.hifood.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

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


        val db = Room.databaseBuilder(
            this, AppDatabase::class.java, "hifood.db"
        ).allowMainThreadQueries()
            .build()

        val produtoDao = db.produtoDao()
//        produtoDao.salva(
//            Produto(
//                nome = "teste nome 3",
//                descricao = "teste de descrição",
//                valor = BigDecimal("10.0")
//            )
//        )

        adapter.atualiza(produtoDao.buscaTodos())
    }

    override fun onResume() {
        super.onResume()
//        adapter.atualiza(dao.buscaTodo())
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