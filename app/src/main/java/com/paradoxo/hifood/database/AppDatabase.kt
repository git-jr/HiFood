package com.paradoxo.hifood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paradoxo.hifood.database.converter.Converters
import com.paradoxo.hifood.database.dao.ProdutoDao
import com.paradoxo.hifood.database.dao.UsuarioDao
import com.paradoxo.hifood.model.Produto
import com.paradoxo.hifood.model.Usuario


@Database(
    entities = [
        Produto::class,
        Usuario::class
    ],

    version = 5,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {

            /* Para fins de estudo, o código a seguir retorna o db direto se ele não for nulo.
            Mas caso seja nulo (?:) Uma instancia e also atribuimos essa instancia ao db para as próxmias chamadas
            Singleton criado!
            */
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java, "hifood.db"
            ).addMigrations(
                MIGRATION_1_2,
                MIGARTION_2_3,
                MIGARTION_3_4,
                MIGRATION_4_5
            ).build()
                .also {
                    db = it
                }
        }
    }
}