package com.paradoxo.hifood.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.extensions.vaiPara
import com.paradoxo.hifood.model.Usuario
import com.paradoxo.hifood.preferences.dataStore
import com.paradoxo.hifood.preferences.usarioLogadoPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity : AppCompatActivity() {


    private val usuarioDAO by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    private var _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected var usuario: StateFlow<Usuario?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usarioLogadoPreferences]?.let { usuarioID ->
                buscaUsuario(usuarioID)
            } ?: vaiParaLogin()
        }
    }

    private suspend fun buscaUsuario(usuarioID: String) {
        _usuario.value = usuarioDAO
            .buscaPorID(usuarioID)
            .firstOrNull()
    }

    protected suspend fun deslogaUsuario() {
        dataStore.edit { preferences ->
            preferences.remove(usarioLogadoPreferences)
        }
    }


    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }
}