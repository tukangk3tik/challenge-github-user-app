package app.githubuserapp.favoritconstumer.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    private const val AUTHORITY = "com.dicoding.picodiploma.githubuserapp"
    private const val SCHEME = "content"

    class FavoritColumns: BaseColumns {

        companion object {
            const val TABLE_NAME = "userfavorites"
            const val _ID = "id"
            const val NAME = "username"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}