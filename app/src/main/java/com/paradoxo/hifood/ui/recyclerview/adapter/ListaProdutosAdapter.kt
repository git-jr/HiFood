package com.paradoxo.hifood.ui.recyclerview.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.paradoxo.hifood.R
import com.paradoxo.hifood.databinding.ProdutoItemBinding
import com.paradoxo.hifood.extensions.tentaCarregarImagem
import com.paradoxo.hifood.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()


    class ViewHolder(binding: ProdutoItemBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        val nome = binding.produtoItemNome
        val descricao = binding.produtoItemDescricao
        val valor = binding.produtoItemValor
        val imagem = binding.produtoItemImageview



        fun vincula(produto: Produto) {

            nome.text = produto.nome
            descricao.text = produto.descricao
            val valorEmMoeda = formataParaMoedaBrasileira(produto.valor)
            valor.text = valorEmMoeda

            val visibilidade = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            imagem.visibility = visibilidade

            imagem.tentaCarregarImagem(produto.imagem)

        }

        private fun formataParaMoedaBrasileira(valor: BigDecimal): String? {
            val formatador: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            return formatador.format(valor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = produtos.size
    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}
