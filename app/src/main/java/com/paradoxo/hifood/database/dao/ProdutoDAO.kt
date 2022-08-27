package com.paradoxo.hifood.database.dao

import androidx.room.*
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): Flow<List<Produto>> // Ao usar Flow como retorno, tirar o Suspend que passa a ser "tranalho" do collect desse emit

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId")
    fun buscaTodosdDoUsuario(usuarioId: String): Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(produto: Produto)

    @Delete
    suspend fun remove(produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Produto?>
}