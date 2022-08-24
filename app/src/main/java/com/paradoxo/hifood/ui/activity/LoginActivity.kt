package com.paradoxo.hifood.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.paradoxo.hifood.database.AppDatabase
import com.paradoxo.hifood.databinding.ActivityLoginBinding
import com.paradoxo.hifood.extensions.vaiPara
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val usuarioDAO by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
        configuraBotaoEntrar()

    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoEntrar.setOnClickListener {
            val usuario = binding.activityLoginUsuario.text.toString()
            val senha = binding.activityLoginSenha.text.toString()
            Log.i("LoginActivity", "onCreate: $usuario - $senha")
            lifecycleScope.launch {
                usuarioDAO.autentica(usuario, senha)?.let { usuario ->
                    vaiPara(ListaProdutosActivity::class.java) {
                        putExtra("CHAVE_USUARIO_ID", usuario.id)
                    }
                } ?: Toast.makeText(
                    this@LoginActivity,
                    "Falha na atenticação",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun configuraBotaoEntrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }
}