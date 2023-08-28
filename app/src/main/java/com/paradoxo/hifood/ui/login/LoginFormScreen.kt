package com.paradoxo.hifood.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.database.dao.UsuarioDao
import com.paradoxo.hifood.extensions.toast
import com.paradoxo.hifood.preferences.dataStore
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates
import com.paradoxo.hifood.ui.components.CustomOutlinedTextField

@Composable
fun LoginFormScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {},
    viewModel: LoginFormViewModel = viewModel(),
    usuarioDao: UsuarioDao
) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp,
            ) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Voltar",
                        tint = colorResource(id = R.color.colorPrimaryContainer)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Precisamos de algumas informações",
                    fontSize = 22.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Bold,
                )

                AnimatedVisibility(
                    visible = state.isFirstAccess,
                    enter = fadeIn(tween(500)) + slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(500)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(500)
                    ) + fadeOut(tween(500))
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextField(
                        label = "Nome", state = state.name,
                        onStateChange = { state.onNameChange(it) }
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    modifier = Modifier.animateContentSize(),
                    label = "E-mail", state = state.email,
                    onStateChange = { state.onEmailChange(it) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(label = "Senha", state = state.password,
                    onStateChange = { state.onPasswordChange(it) }
                )

                Spacer(modifier = Modifier.height(8.dp))
                val message =
                    if (state.isFirstAccess) "Já tem uma conta? Faça login" else "Ainda não tem uma conta? Cadastre-se"
                TextButton(onClick = {
                    viewModel.showNameField()
                }) {
                    Text(
                        text = message,
                        color = colorResource(id = R.color.colorPrimaryContainer),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }


            Column(
                modifier = Modifier.imePadding()
            ) {
                Text(
                    text = "O HiFood poderá enviar comunicações neste e-mail, pra cancelar a inscrição acess 'Configurações'.",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                val backgroundButtonColor =
                    if (state.allFieldIsOk) colorResource(id = R.color.colorPrimaryContainer)
                    else Color(244, 244, 244)
                val contentColorButtonColor =
                    if (state.allFieldIsOk) Color.White else Color.LightGray

                val context = LocalContext.current
                val dataStore = context.dataStore
                Button(
                    onClick = {
                        if (state.allFieldIsOk) {
                            if (state.isFirstAccess) {
                                viewModel.saveUser(
                                    dataStore = dataStore,
                                    usuarioDao = usuarioDao,
                                    onSuccess = {
                                        context.toast("Cadastro realizado com sucesso")
                                        onContinueClick()
                                    },
                                    onError = {
                                        context.toast("Falha ao cadastrar usuário")
                                    },
                                )
                            } else {
                                viewModel.loggin(
                                    usuarioDao = usuarioDao,
                                    dataStore = dataStore,
                                    onSuccess = {
                                        context.toast("Login realizado com sucesso")
                                        onContinueClick()
                                    },
                                    onError = {
                                        context.toast("Falha ao realizar login")
                                    },
                                )
                            }
                        } else {
                            context.toast("Preencha todos os campos")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = backgroundButtonColor,
                        contentColor = contentColorButtonColor
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(
                        text = "Continuar",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontFamily = MontserratAlternates,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginFormScreenPreview() {
    HiFoodTheme {
        LoginFormScreen(
            onBackClick = {},
            onContinueClick = {},
            usuarioDao = AppDatabase.instancia(LocalContext.current).usuarioDao()
        )
    }
}