package com.gmail.vladkhinski.homework_app.retrofit

import com.gmail.vladkhinski.homework_app.repository.CharacterRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object BreakingBadService {

    private val retrofit by lazy(LazyThreadSafetyMode.NONE) { provideRetrofit() }

    private val breakingBadApi by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create<BreakingBadApi>()
    }

    fun provideRepository(): CharacterRepository {
        return CharacterRepository(breakingBadApi)
    }

    fun provideBreakingBadApi(): BreakingBadApi{
        return breakingBadApi
    }

    private fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl("https://www.breakingbadapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }
}
