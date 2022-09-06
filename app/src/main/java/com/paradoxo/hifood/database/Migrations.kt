package com.paradoxo.hifood.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


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

val MIGARTION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Produto ADD COLUMN 'usuarioId' TEXT")
    }
}

val MIGARTION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Produto ADD COLUMN 'sincronizado' INTEGER NOT NULL  DEFAULT 0")
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Produto ADD COLUMN 'desativado' INTEGER NOT NULL  DEFAULT 0")
    }
}