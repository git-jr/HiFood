package com.paradoxo.hifood.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.database.dao.ProdutoDao
import com.paradoxo.hifood.databinding.ActivityFormularioProdutoBinding
import com.paradoxo.hifood.extensions.tentaCarregarImagem
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.repository.ProdutoRepository
import com.paradoxo.hifood.ui.dialog.FormularioImagemDialog
import com.paradoxo.hifood.webclient.ProdutoWebClient
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null
    private var produtoId: String? = null

    private val respository by lazy {
        ProdutoRepository(
            AppDatabase.instancia(this).produtoDao(),
            ProdutoWebClient()
        )
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

            produtoId?.let { id ->
                respository.buscaPorId(id).collect { produto ->
                    produto?.let {
                        title = "Alterar produto"
                        preencheCampos(it)
                    }
                }
            }
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getStringExtra(CHAVE_PRODUTO_ID)
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
                    respository.salva(produtoNovo)
                }
                finish()
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

        return produtoId?.let { id ->
            Produto(
                id = id,
                nome = nome,
                descricao = descricao,
                valor = valor,
                imagem = url,
                usuarioId = usuarioId
            )
        } ?: Produto(
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url,
            usuarioId = usuarioId,
            sincronizado = false
        )

    }

}