package com.paradoxo.hifood.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.paradoxo.hifood.R
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates
import java.math.BigDecimal

class FormularioProdutoComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiFoodTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FormularioProduto()
                }
            }
        }
    }
}

@Composable
fun FormularioProduto() {
    Scaffold(
        topBar = {
            FormularioProdutoAppBar()

        },
        content = { paddingValues -> FormularioProdutoConteudo(Modifier.padding(paddingValues)) }
    )
}

@Composable
fun FormularioProdutoAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.cadastrar_produto),
                color = Color.White,
                fontFamily = MontserratAlternates
            )
        }
    )
}

@Composable
fun FormularioProdutoConteudo(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        val imageHeight = 200.dp
        val boxheight = 230.dp

        Box(modifier = Modifier.height(boxheight)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.imagem_padrao)
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
        }

        Column(Modifier.padding(16.dp)) {
            var nome by remember { mutableStateOf("") }
            var descricao by remember { mutableStateOf("") }
            var valor by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = valor,
                onValueChange = {
                    valor = it
                },
                label = { Text("Valor") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
                Button(modifier = Modifier
                    .fillMaxWidth(),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Salvar")
                }
            }
        }


    }
}


@Preview
@Composable
fun FormularioProdutoPreview() {
    FormularioProduto()
}