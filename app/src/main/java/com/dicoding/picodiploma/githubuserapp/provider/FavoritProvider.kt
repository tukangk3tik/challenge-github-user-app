package com.dicoding.picodiploma.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.picodiploma.githubuserapp.db.FavoritDao
import com.dicoding.picodiploma.githubuserapp.db.FavoritDatabase
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class FavoritProvider: ContentProvider() {

    companion object {
        private const val TABLE_NAME = "userfavorites"
        private const val AUTHORITY = "com.dicoding.picodiploma.githubuserapp"
        private const val FAVORIT = 1
        private const val FAVORIT_ID = 2
        private val sURiMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoritDb: FavoritDao

        init {
            sURiMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORIT)
            sURiMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORIT_ID)
        }
    }

    override fun onCreate(): Boolean {
        favoritDb = FavoritDatabase.getInstance(context as Context).getFavoritDao()
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when(sURiMatcher.match(uri)) {
            FAVORIT_ID -> {
                val context = context ?: return 0
                val count: Int = runBlocking {
                    favoritDb.deleteById(uri.lastPathSegment!!.toInt())
                }
                context.contentResolver.notifyChange(uri, null)
                count
            }
            else -> throw IllegalArgumentException("Unknow URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    //no insert for consumer, because insert only doing from provider apps
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(sURiMatcher.match(uri)) {
            FAVORIT -> {
                try {
                    favoritDb.getAllProvider()
                } catch (e: Exception) {
                    print("Error: $e")
                    null
                }
            }
            FAVORIT_ID -> {
                return runBlocking {
                    try {
                        favoritDb.findOneById(uri.lastPathSegment!!.toInt()) as Cursor
                    } catch (e: Exception) {
                        print("Error: $e")
                        null
                    }
                }
            }
            else -> null
        }
    }

    //no update for consumer, because update only doing from provider apps
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}