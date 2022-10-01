package com.paradoxo.hifood.database.dao

import androidx.room.*
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(produto: Produto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(produto: List<Produto?>)

    @Query("SELECT * FROM Produto WHERE desativado = 0")
    fun buscaTodos(): Flow<List<Produto>> // Ao usar Flow como retorno, tirar o Suspend que passa a ser "tranalho" do collect desse emit

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId AND desativado = 0")
    fun buscaTodosdDoUsuario(usuarioId: String): Flow<List<Produto>>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId AND desativado = 0")
    suspend fun buscaTodosdDoUsuarioSemFlow(usuarioId: String): List<Produto>

    @Query("DELETE FROM Produto WHERE id = :id")
    suspend fun remove(id: String)

    @Query("SELECT * FROM Produto WHERE id = :id AND desativado = 0")
    fun buscaPorId(id: String): Flow<Produto?>

    @Query("SELECT * FROM Produto WHERE sincronizado = 0 AND desativado = 0")
    fun buscaNaoSincronizados(): Flow<List<Produto>>

    @Query("UPDATE Produto SET desativado = 1 WHERE id = :id")
    suspend fun desativa(id: String)

    @Query("SELECT * FROM Produto WHERE desativado = 1")
    fun buscaDesativado(): Flow<List<Produto>>

}