package com.paradoxo.hifood.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """CREATE TABLE IF NOT EXISTS `Usuario`(
            `id` TEXT NOT NULL,
            `nome` TEXT NOT NULL, 
            `senha` TEXT NOT NULL, PRIMARY KEY(`id`))"""
        )
    }

}