package com.example.advanceenglishdictionary.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class PhrasesDatabaseHelper(private val context: Context) {

    private val dbName = "db_prahse.db"
    private val dbPath: String = context.getDatabasePath(dbName).path

    companion object {
        // 🔥 Toggle this for development / production
        private const val FORCE_COPY = true
    }

    init {
        copyDatabaseIfNeeded()
    }

    /** Copy database (with optional overwrite) */
    private fun copyDatabaseIfNeeded() {
        val dbFile = File(dbPath)

        // ✅ If force copy is ON → delete old DB
        if (FORCE_COPY && dbFile.exists()) {
            dbFile.delete()
        }

        // ✅ Copy only if DB doesn't exist
        if (!dbFile.exists()) {
            dbFile.parentFile?.mkdirs()

            try {
                val inputStream: InputStream = context.assets.open(dbName)
                val outputStream = FileOutputStream(dbFile)

                val buffer = ByteArray(1024)
                var length: Int

                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /** Open the database */
    fun openDatabase(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
    }
}