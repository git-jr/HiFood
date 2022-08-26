package com.paradoxo.hifood.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.vaiPara(clazz: Class<*>, intent: Intent.() -> Unit = {}) {
    Intent(this, clazz)
        .apply {
            intent()
            startActivity(this)
        }
}

fun Context.toast(mensagem: String){
    Toast.makeText(
        this,
        mensagem,
        Toast.LENGTH_SHORT
    ).show()
}