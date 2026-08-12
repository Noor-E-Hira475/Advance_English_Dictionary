package com.example.advanceenglishdictionary.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class DictionaryDatabaseHelper(private val context: Context) {

    private val dbName = "dictionary.db"
    private val dbPath: String = context.getDatabasePath(dbName).path

    companion object {
        private const val FORCE_COPY = false
    }

    init {
        copyDatabaseIfNeeded()
    }

    private fun copyDatabaseIfNeeded() {
        val dbFile = File(dbPath)

        if (FORCE_COPY && dbFile.exists()) {
            dbFile.delete()
        }

        if (!dbFile.exists()) {
            dbFile.parentFile?.mkdirs()

            try {
                val inputStream: InputStream = context.assets.open(dbName)
                val outputStream = FileOutputStream(dbFile)

                val buffer = ByteArray(1024 * 8)
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

    fun openDatabase(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
    }
}
