package com.paradoxo.hifood.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.util.*

@Entity
@Parcelize
data class Produto(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val imagem: String? = null,
    val usuarioId: String? = null,
    @ColumnInfo(defaultValue = "0")
    val sincronizado: Boolean = false,
    @ColumnInfo(defaultValue = "0")
    val desativado: Boolean = false,
) : Parcelable

