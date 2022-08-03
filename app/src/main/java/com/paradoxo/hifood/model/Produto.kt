package com.paradoxo.hifood.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Produto(
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val imagem: String? = null
) : Parcelable
