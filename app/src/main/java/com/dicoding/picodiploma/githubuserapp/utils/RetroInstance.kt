package com.dicoding.picodiploma.githubuserapp.utils

import com.dicoding.picodiploma.githubuserapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroInstance {
    companion object {
        private const val BASE_URL = "https://api.github.com/"

        private val headerInterceptor = Interceptor { chain ->
            var request = chain.request()

            request = request.newBuilder()
                .addHeader("Authorization", BuildConfig.API_KEY)
                .build()

            chain.proceed(request)
        }

        private val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
            }).addInterceptor(headerInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


        fun buildRetrofit(): Retrofit
                = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
    }
}