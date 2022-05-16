package com.gmail.vladkhinski.homework_app.retrofit

import com.gmail.vladkhinski.homework_app.model.Characters
import retrofit2.http.GET
import retrofit2.http.Path

interface BreakingBadApi {

    @GET("characters")
    suspend fun getCharacters(
    ): List<Characters>

    @GET("characters/{char_id}")
    suspend fun getCharacterDetails(
        @Path("char_id") char_id: Long,
    ): List<Characters>


}
