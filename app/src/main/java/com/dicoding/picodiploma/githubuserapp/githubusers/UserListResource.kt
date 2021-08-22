package com.dicoding.picodiploma.githubuserapp.githubusers

sealed class UserListResource<T> (
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?): UserListResource<T>(data)
    class Error<T>(message: String?, data: T? = null): UserListResource<T>(data, message)
    class Loading<T>: UserListResource<T>()
}
