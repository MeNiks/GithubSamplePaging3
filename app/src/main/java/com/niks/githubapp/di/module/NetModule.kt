package com.niks.githubapp.di.module

import com.google.gson.Gson
import com.niks.githubapp.BuildConfig
import com.niks.githubapp.di.scope.PerApplication
import com.niks.githubapp.network.GithubApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetModule {

    @Provides
    @PerApplication
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient
            .Builder()
            .callTimeout(CALL_TIME_OUT_SEC, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            okHttpClient.addInterceptor(httpLoggingInterceptor)
        }
        return okHttpClient.build()
    }

    @Provides
    @PerApplication
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .callFactory(okHttpClient)
            .build()
    }

    @Provides
    @PerApplication
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    companion object {
        private const val CALL_TIME_OUT_SEC = 20L
    }
}