package app.githubuserapp.favoritconstumer.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoritEntity (
    var username : String? = null,
    var avatar_url : String? = null,
    var id : Long = 0
) : Parcelable