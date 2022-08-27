package com.paradoxo.hifood.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.database.dao.ProdutoDAO
import com.paradoxo.hifood.databinding.ActivityFormularioProdutoBinding
import com.paradoxo.hifood.extensions.tentaCarregarImagem
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao: ProdutoDAO by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }

    private val usuarioDAO by lazy {
        AppDatabase.instancia(this).usuarioDao()
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
        lifecycleScope.launch {

            produtoDao.buscaPorId(produtoId).collect { produto ->
                produto?.let {
                    title = "Alterar produto"
                    preencheCampos(it)
                }
            }
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
            lifecycleScope.launch {
                usuario.value?.let { usuario ->
                    val produtoNovo = criaProduto(usuario.id)
                    produtoDao.salva(produtoNovo)
                    finish()
                }
            }

        }
    }

    private fun criaProduto(usuarioId: String): Produto {
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
            imagem = url,
            usuarioId = usuarioId
        )
    }

}