package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.paradoxo.hifood.R
import com.paradoxo.hifood.extensions.formataParaMoedaBrasileira
import com.paradoxo.hifood.extensions.vaiPara
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.sampleData.sampleDataProduct
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates
import com.paradoxo.hifood.ui.activity.ui.theme.corExataDosTextosEmView

class ListaProdutosComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiFoodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    ProductListScreen(
                        sampleDataProduct,
                        onClickItem = { produtoClicado ->
                            vaiParaDetalhes(produtoClicado)
                        },
                        irParaCadastro = {
                            vaiPara(FormularioProdutoComposeActivity::class.java)
                        }
                    )
                }
            }
        }
    }

    fun vaiParaDetalhes(produto: Produto) {
        val intent = Intent(this, DetalhesProdutoComposeActivity::class.java)
        intent.putExtra(CHAVE_PRODUTO_ID, produto.id)
        startActivity(intent)
    }
}


@Composable
fun ProductListScreen(
    produtos: List<Produto>,
    onClickItem: (Produto) -> Unit,
    irParaCadastro: () -> Unit
) {
    Scaffold(
        topBar = {
            HomeAppBar()
        },
        content = { paddingValues ->
            ListaProdutos(Modifier.padding(paddingValues),
                produtos = produtos,
                onClickItem = { produtoClicado ->
                    onClickItem(produtoClicado)
                })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(text = {
                Text(stringResource(id = R.string.novo_produto), fontFamily = MontserratAlternates)
            }, icon = {
                Icon(Icons.Default.Add, contentDescription = "Botão adicionar novo produto")
            }, onClick = irParaCadastro)
        })
}

@Composable
private fun HomeAppBar() {
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.White,
            fontFamily = MontserratAlternates
        )
    }, actions = {
        IconButton(onClick = { TODO() }) {
            Icon(
                Icons.Default.ExitToApp,
                tint = Color.White,
                contentDescription = "Icone Deslogar"
            )
        }
    })
}


@Composable
fun ListaProdutos(
    modifier: Modifier = Modifier, produtos: List<Produto>, onClickItem: (Produto) -> Unit
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(produtos) { produto ->
            ItemProduto(produto) { produtoClicado ->
                onClickItem(produtoClicado)
            }
        }
    }
}

@Composable
fun ItemProduto(produto: Produto, onClickItem: (Produto) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(IntrinsicSize.Max)
            .clickable { onClickItem(produto) },
        elevation = 8.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp),

        ) {
        Row {
            AsyncImage(
                modifier = Modifier.weight(3f),
                model = ImageRequest.Builder(LocalContext.current).data(produto.imagem).build(),
                error = painterResource(id = R.drawable.erro),
                placeholder = rememberDrawablePainter(
                    ContextCompat.getDrawable(
                        LocalContext.current, R.drawable.placeholder
                    )
                ),
                contentDescription = "Imagem do Produto",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(7f),
            ) {
                Text(
                    text = produto.nome,
                    fontSize = 20.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = corExataDosTextosEmView
                )
                Text(
                    text = produto.descricao,
                    fontSize = 14.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = corExataDosTextosEmView

                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = produto.valor.formataParaMoedaBrasileira(),
                    fontSize = 18.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = android.R.color.holo_green_dark),
                )
            }
        }
    }

}


@Preview
@Composable
fun ListaProdutosPreview() {
    ListaProdutos(produtos = sampleDataProduct, onClickItem = {})
}

@Preview
@Composable
fun ProductListScreenPreview() {
    HiFoodTheme {
        ProductListScreen(sampleDataProduct, {}, {})
    }

}