package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import java.math.BigDecimal
import com.paradoxo.hifood.R
import com.paradoxo.hifood.extensions.formataParaMoedaBrasileira
import com.paradoxo.hifood.sampleData.sampleDataProduct
import com.paradoxo.hifood.ui.activity.ui.theme.Montserrat
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates
import com.paradoxo.hifood.ui.activity.ui.theme.corExataDosTextosEmView
import com.paradoxo.hifood.ui.util.CHAVE_PRODUTO_ID

class DetalhesProdutoComposeActivity : ComponentActivity() {

    private var produtoId: Long = 0L
    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tentaCarregarProduto()
        setContent {
            HiFoodTheme {
//                var produtoState: Produto? by remember {
//                    mutableStateOf(
//                        produtoDao.buscaPorId(
//                            produtoId
//                        )
//                    )
//                }

//                produtoState?.let { produto ->
                DetalhesProdutoTela(
                    sampleDataProduct.get(0),
                    onEditProdutoClick = {
                        Intent(this, FormularioProdutoActivity::class.java).apply {
                            putExtra(CHAVE_PRODUTO_ID, produtoId)
                            startActivity(this)
                        }
                    },
                    onDeleteProdutoClick = {
                        // produtoState.let { produtoDao.remove(produto) }
                        finish()
                    }
                )
                // } ?: finish()

                BuscaProduto(onResume = {
                    // produtoState = produtoDao.buscaPorId(produtoId)
                })
            }
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }
}

@Composable
private fun BuscaProduto(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onResume: () -> Unit,
) {
    val currentOnResume by rememberUpdatedState(onResume)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                currentOnResume()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun DetalhesProdutoTela(
    produto: Produto,
    onEditProdutoClick: () -> Unit = {},
    onDeleteProdutoClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            DetalhesAppBar(
                onEditProdutoClick,
                onDeleteProdutoClick
            )
        },
        content = { paddingValues ->
            DetalhesConteudo(Modifier.padding(paddingValues), produto)
        }
    )
}

@Composable
fun DetalhesAppBar(
    onEditProdutoClick: () -> Unit = {},
    onDeleteProdutoClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                fontFamily = MontserratAlternates
            )
        },
        actions = {
            IconButton(onClick = {
                onEditProdutoClick()
            }) {
                Icon(
                    painterResource(R.drawable.ic_action_editar),
                    contentDescription = "Editar produto",
                    tint = Color.White
                )
            }
            IconButton(onClick = { onDeleteProdutoClick() }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Deletar produto",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun DetalhesConteudo(modifier: Modifier = Modifier, produto: Produto) {
    Column(modifier) {
        val imageHeight = 200.dp
        val boxheight = 230.dp

        Box(modifier = Modifier.height(boxheight)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(produto.imagem)
                    .build(),
                error = painterResource(id = R.drawable.erro),
                placeholder = rememberDrawablePainter(
                    ContextCompat.getDrawable(
                        LocalContext.current,
                        R.drawable.placeholder
                    )
                ),
                contentDescription = "Imagem do Produto",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(imageHeight),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )

            Surface(
                shape = CircleShape,
                color = Color.White,
                elevation = 10.dp,
                modifier = Modifier
                    .offset(y = 180.dp, x = 16.dp)

            ) {
                Text(
                    text = produto.valor.formataParaMoedaBrasileira(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(android.R.color.holo_green_dark),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }

        Column {
            Text(
                text = produto.nome,
                fontSize = 28.sp,
                color = corExataDosTextosEmView,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )

            Text(
                text = produto.descricao,
                fontSize = 20.sp,
                color = corExataDosTextosEmView,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetalhesAppBarPreview() {
    HiFoodTheme() {
        DetalhesAppBar()
    }
}

@Preview(showBackground = true)
@Composable
fun DetalhesConteudoPreview() {

    DetalhesConteudo(
        Modifier,
        Produto(
            nome = LoremIpsum(10).values.first(),
            descricao = LoremIpsum(50).values.first(),
            imagem = "",
            valor = BigDecimal(42)
        )
    )
}