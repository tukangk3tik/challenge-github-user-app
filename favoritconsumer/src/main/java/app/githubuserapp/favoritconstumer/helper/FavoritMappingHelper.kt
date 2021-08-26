package app.githubuserapp.favoritconstumer.helper

import android.database.Cursor
import app.githubuserapp.favoritconstumer.db.DatabaseContract
import app.githubuserapp.favoritconstumer.db.FavoritEntity

object FavoritMappingHelper {

    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<FavoritEntity> {
        val usersList = ArrayList<FavoritEntity>()

        usersCursor?.apply {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseContract.FavoritColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoritColumns.NAME))
                val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.FavoritColumns.AVATAR_URL))
                usersList.add(FavoritEntity(username, avatar_url, id))
            }
        }
        return usersList
    }

    fun mapCursorToObject(usersCursor: Cursor?): FavoritEntity {
        var user = FavoritEntity()

        usersCursor?.apply {
            moveToFirst()
            val id = getLong(getColumnIndexOrThrow(DatabaseContract.FavoritColumns._ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoritColumns.NAME))
            val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.FavoritColumns.AVATAR_URL))
            user = FavoritEntity(username, avatar_url, id)
        }

        return user
    }

}