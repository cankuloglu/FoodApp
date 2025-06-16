package com.example.foodapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.foodapp.RecipeDao
import com.example.foodapp.RecipeEntity
import com.example.foodapp.domain.util.Converters

@Database(entities = [RecipeEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("ALTER TABLE favorite_recipes ADD COLUMN timestamp INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE favorite_recipes_new (
                id INTEGER PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                image TEXT NOT NULL,
                timestamp INTEGER
            )
        """.trimIndent())


        db.execSQL("""
            INSERT INTO favorite_recipes_new (id, title, image, timestamp)
            SELECT id, title, image, timestamp FROM favorite_recipes
        """.trimIndent())


        db.execSQL("DROP TABLE favorite_recipes")


        db.execSQL("ALTER TABLE favorite_recipes_new RENAME TO favorite_recipes")
    }
}
