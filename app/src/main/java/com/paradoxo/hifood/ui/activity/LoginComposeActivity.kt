package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.database.dao.UsuarioDao
import com.paradoxo.hifood.extensions.vaiPara
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates
import com.paradoxo.hifood.ui.login.LoginFormScreen
import com.paradoxo.hifood.ui.login.LoginScreenStateViewModel

class LoginComposeActivity : ComponentActivity() {

    private val usuarioDao: UsuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startActivity(Intent(this, NewHomeActivity::class.java))

        setContent {
            val viewModel = LoginScreenStateViewModel()
            val state by viewModel.uiState.collectAsState()

            val context = LocalContext.current

            HiFoodTheme {
                Box(
                    Modifier.safeGesturesPadding()
                ) {
                    AnimatedVisibility(
                        visible = !state.gotToFormScreen,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        LoginScreen(
                            onGoToFormScreen = {
                                viewModel.goToFormScreen()
                            }
                        )
                    }

                    AnimatedVisibility(
                        visible = state.gotToFormScreen,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        LoginFormScreen(
                            usuarioDao = usuarioDao,
                            onBackClick = {
                                viewModel.goToLoginScreen()
                            },
                            onContinueClick = {
                                context.vaiPara(ListaProdutosActivity::class.java)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onGoToFormScreen: () -> Unit = {}
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner_hifood),
            contentDescription = "Banner com logo do HiFood e vários tipos de comida",
            modifier = Modifier
                .weight(1.5F)
                .fillMaxWidth()
        )

        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Falta pouco pra matar sua fome",
                fontSize = 24.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Como deseja continuar?",
                fontSize = 16.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )


            // Google Button
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(66, 133, 244, 255), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Box(
                    Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/COxitqgJr1sJnIDe8-jiKhxDx1FrYbtRHKJ9z_hELisAlapwE9LUPh6fcXIfb5vwpbMl4xl9H9TRFPc5NOO8Sb3VSgIBrfRYvW6cUA",
                        contentDescription = "Logo do Google",
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    )
                    Text(
                        text = "Continuar com o Google",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = MontserratAlternates,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // "Outras opções"
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .clickable {
                        onGoToFormScreen()
                    }
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray.copy(0.5f), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Outras opções",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Continuar como convidado",
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = MontserratAlternates,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    HiFoodTheme {
        LoginScreen()
    }
}


