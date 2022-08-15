package com.paradoxo.hifood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paradoxo.hifood.database.converter.Converters
import com.paradoxo.hifood.database.dao.ProdutoDAO
import com.paradoxo.hifood.model.Produto


@Database(entities = [Produto::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDAO

    companion object {
        @Volatile private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {

            /* Para fins de estudo, o código a seguir retorna o db direto se ele não for nulo.
            Mas caso seja nulo (?:) Uma instancia e also atribuimos essa instancia ao db para as próxmias chamadas
            Singleton criado!
            */
            return db ?: Room.databaseBuilder(
                context, AppDatabase::class.java, "hifood.db"
            ).build()
                .also {
                    db = it
                }
        }
    }
}