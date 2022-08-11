package com.paradoxo.hifood.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.database.dao.ProdutoDAO
import com.paradoxo.hifood.databinding.ActivityFormularioProdutoBinding
import com.paradoxo.hifood.extensions.tentaCarregarImagem
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao: ProdutoDAO by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Cadastrar Produto"
        configuraBotaoSalvar()
        setContentView(binding.root)
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(imagem)
            }
        }

        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        produtoDao.buscaPorId(produtoId)?.let {
            title = "Alterar produto"
            preencheCampos(it)
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencheCampos(produto: Produto) {
        url = produto.imagem
        binding.activityFormularioProdutoImagem
            .tentaCarregarImagem(produto.imagem)
        binding.activityFormularioProdutoNome
            .setText(produto.nome)
        binding.activityFormularioProdutoDescricao
            .setText(produto.descricao)
        binding.activityFormularioProdutoValor
            .setText(produto.valor.toPlainString())
    }


    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar

        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            produtoDao.salva(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricaoa = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricaoa.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()

        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }

}