package com.paradoxo.hifood.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.paradoxo.hifood.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert()
    suspend fun salva(usuario: Usuario)

    @Query(
        """
        SELECT * FROM usuario
        WHERE id = :usarioID
        AND senha =:senha"""
    )
    suspend fun autentica(
        usarioID: String,
        senha: String
    ): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :usuarioId")
    fun buscaPorID(usuarioId: String): Flow<Usuario>
}