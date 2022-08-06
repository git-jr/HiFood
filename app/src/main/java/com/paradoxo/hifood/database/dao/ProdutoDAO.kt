package com.paradoxo.hifood.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paradoxo.hifood.model.Produto

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): List<Produto>

    @Insert
    fun salva(produto: Produto)
}