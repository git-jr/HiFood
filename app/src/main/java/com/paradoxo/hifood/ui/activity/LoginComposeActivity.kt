package com.paradoxo.hifood.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.paradoxo.hifood.R
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates
import kotlinx.coroutines.flow.MutableStateFlow

class LoginComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = LoginScreenStateViewModel()
            val state by viewModel.uiState.collectAsState()

            HiFoodTheme {
                Box(
                    Modifier.safeGesturesPadding()
                ) {
                    if (!state.gotToFormScreen) {
                        LoginScreen(
                            onGoToFormScreen = {
                                viewModel.goToFormScreen()
                            }
                        )
                    }

                    if (state.gotToFormScreen) {
                        LoginFormScreen(
                            state = LoginFormState(),
                            onBackClick = {
                                viewModel.goToLoginScreen()
                            },
                            onContinueClick = { /*TODO*/ }
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


@Composable
fun LoginFormScreen(
    modifier: Modifier = Modifier,
    state: LoginFormState,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    var nameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }

    var allFieldsIsOk by remember { mutableStateOf(false) }

    LaunchedEffect(emailState, passwordState, nameState) {
        allFieldsIsOk =
            emailState.isNotBlank() && passwordState.isNotBlank() && nameState.isNotBlank()
    }

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
                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    label = "Nome", state = nameState,
                    onStateChange = { nameState = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    label = "E-mail", state = emailState,
                    onStateChange = { emailState = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(label = "Senha", state = passwordState,
                    onStateChange = { passwordState = it }
                )
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
                    if (allFieldsIsOk) colorResource(id = R.color.colorPrimaryContainer)
                    else Color(244, 244, 244)
                val contentColorButtonColor =
                    if (allFieldsIsOk) Color.White else Color.LightGray

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
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

@Composable
private fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    label: String,
    state: String,
    onStateChange: (String) -> Unit = {}
) {
    var internalState by remember { mutableStateOf(state) }
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = internalState,
            onValueChange = {
                internalState = it
                onStateChange(it)
            },
            label = {
                Text(
                    text = label,
                    color = Color.Gray,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
                cursorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black
            ),
            placeholder = {
                Text(
                    text = label,
                    color = Color.LightGray,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(52.dp),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    HiFoodTheme {
        LoginScreen()
    }
}


data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val emailAndPasswordIsOk: Boolean = false
)


data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val emailAndPasswordIsOk: Boolean = false,
    val gotToFormScreen: Boolean = false
)

class LoginScreenStateViewModel : ViewModel() {
    fun goToFormScreen() {
        _uiState.value = uiState.value.copy(gotToFormScreen = true)
    }

    fun goToLoginScreen() {
        _uiState.value = uiState.value.copy(gotToFormScreen = false)
    }

    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState
}