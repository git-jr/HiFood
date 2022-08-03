package com.paradoxo.hifood.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paradoxo.hifood.R
import com.paradoxo.hifood.databinding.ActivityDetalhesProdutoBinding
import com.paradoxo.hifood.extensions.formataParaMoedaBrasileira
import com.paradoxo.hifood.extensions.tentaCarregarImagem
import com.paradoxo.hifood.model.Produto

class DetalhesProdutoActivity : AppCompatActivity(R.layout.activity_detalhes_produto) {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val produtoRecuperado = intent.getParcelableExtra<Produto>("produto")
        carregaProdutoNaTela(produtoRecuperado)
    }

    private fun carregaProdutoNaTela(produtoRecuperado: Produto?) {
        produtoRecuperado?.apply {
            binding.apply {
                activityDetalhesProdutoImagem.tentaCarregarImagem(imagem)
                activityDetalhesProdutoTitulo.text = nome
                activityDetalhesProdutoDescricao.text = descricao
                activityDetalhesProdutoValor.text = valor.formataParaMoedaBrasileira()
            }
        }
    }
}