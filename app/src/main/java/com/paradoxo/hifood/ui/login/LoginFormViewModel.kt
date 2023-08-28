package com.paradoxo.hifood.ui.login

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradoxo.hifood.database.dao.UsuarioDao
import com.paradoxo.hifood.model.Usuario
import com.paradoxo.hifood.preferences.usarioLogadoPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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