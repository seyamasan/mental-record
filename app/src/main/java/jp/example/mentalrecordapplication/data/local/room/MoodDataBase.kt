package jp.example.mentalrecordapplication.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 2,
    entities = [MoodEntity::class],
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class MoodDataBase: RoomDatabase() {
    abstract  fun moodDao(): MoodDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 新しいテーブルを一時的に作成
        db.execSQL("""
            CREATE TABLE mood_table_tmp (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                mood INTEGER NOT NULL,
                date TEXT NOT NULL,
                time_of_day INTEGER NOT NULL,
                memo  TEXT NULL
            )
        """.trimIndent())

        // 元のテーブルのデータを変換して挿入
        db.execSQL("""
            INSERT INTO mood_table_tmp (id, mood, date, time_of_day, memo)
            SELECT 
                id,
                CASE mood
                    WHEN '喜' THEN 0
                    WHEN '怒' THEN 1
                    WHEN '哀' THEN 2
                    WHEN '楽' THEN 3
                    WHEN '普通' THEN 4
                    WHEN 'Happy' THEN 0
                    WHEN 'Angry' THEN 1
                    WHEN 'Sad' THEN 2
                    WHEN 'Fun' THEN 3
                    WHEN 'Normal' THEN 4
                    ELSE 4
                END AS mood,
                date,
                CASE time_zone
                    WHEN '朝' THEN 0
                    WHEN '昼' THEN 1
                    WHEN '夜' THEN 2
                    WHEN 'Morning' THEN 0
                    WHEN 'Noon' THEN 1
                    WHEN 'Night' THEN 2
                    ELSE 1
                END AS time_of_day,
                memo
            FROM mood_table
        """.trimIndent())

        // 元のテーブルを削除
        db.execSQL("DROP TABLE mood_table")

        // 一時的に作ったテーブルを元の名前に変更
        db.execSQL("ALTER TABLE mood_table_tmp RENAME TO mood_table")
    }
}