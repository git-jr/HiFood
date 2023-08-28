package com.paradoxo.hifood.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.paradoxo.hifood.R
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.database.dao.UsuarioDao
import com.paradoxo.hifood.extensions.toast
import com.paradoxo.hifood.extensions.vaiPara
import com.paradoxo.hifood.model.Usuario
import com.paradoxo.hifood.preferences.dataStore
import com.paradoxo.hifood.preferences.usarioLogadoPreferences
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginComposeActivity : ComponentActivity() {

    private val usuarioDao: UsuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    val name: String = "",
    val isFirstAccess: Boolean = false,
    val allFieldIsOk: Boolean = false,
    val onNameChange: (String) -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
)

class LoginFormViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginFormState())
    val uiState: StateFlow<LoginFormState>
        get() = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onNameChange = { name ->
                    _uiState.value = _uiState.value.copy(name = name)
                    verifyAllFieldsIsOk()
                },
                onEmailChange = { email ->
                    _uiState.value = _uiState.value.copy(email = email)
                    verifyAllFieldsIsOk()
                },
                onPasswordChange = { password ->
                    _uiState.value = _uiState.value.copy(password = password)
                    verifyAllFieldsIsOk()
                }
            )
        }
    }


    private fun verifyAllFieldsIsOk() {
        val checkName = _uiState.value.isFirstAccess
        val listChecks = listOf(
            _uiState.value.email.isNotBlank(),
            _uiState.value.password.isNotBlank(),
            if (checkName) _uiState.value.name.isNotBlank() else true
        )

        _uiState.value = _uiState.value.copy(
            allFieldIsOk = listChecks.all { it }
        )
    }

    fun showNameField() {
        _uiState.value = _uiState.value.copy(
            isFirstAccess = !_uiState.value.isFirstAccess,
            name = ""
        )
        verifyAllFieldsIsOk()
    }


    fun saveUser(
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {},
        usuarioDao: UsuarioDao,
        dataStore: DataStore<Preferences>
    ) {
        with(_uiState.value) {
            val usuario = Usuario(email, name, password)
            viewModelScope.launch {
                try {
                    usuarioDao.salva(usuario)
                    onSuccess()
                    loggin(dataStore = dataStore, usuarioDao = usuarioDao)
                } catch (e: Exception) {
                    Log.e("Cadastro Usuário", "configuraBotaoCadastrar: ", e)
                    onError()
                }
            }
        }
    }

    fun loggin(
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {},
        usuarioDao: UsuarioDao,
        dataStore: DataStore<Preferences>
    ) {
        val usuario = _uiState.value.email
        val senha = _uiState.value.password

        viewModelScope.launch {
            usuarioDao.autentica(usuario, senha)?.let { usuario ->
                dataStore.edit { preferences ->
                    preferences[usarioLogadoPreferences] = usuario.id
                }
                onSuccess()
            } ?: onError()
        }
    }
}


data class LoginScreenState(
    val gotToFormScreen: Boolean = false
)

class LoginScreenStateViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState.asStateFlow()

    fun goToFormScreen() {
        _uiState.value = uiState.value.copy(gotToFormScreen = true)
    }

    fun goToLoginScreen() {
        _uiState.value = uiState.value.copy(gotToFormScreen = false)
    }

}