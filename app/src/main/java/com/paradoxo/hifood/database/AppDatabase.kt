package com.paradoxo.hifood.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paradoxo.hifood.database.converter.Converters
import com.paradoxo.hifood.database.dao.ProdutoDAO
import com.paradoxo.hifood.model.Produto


@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDAO
}