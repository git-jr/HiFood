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
        fun instancia(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java, "hifood.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}