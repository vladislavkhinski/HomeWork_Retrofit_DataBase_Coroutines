package com.gmail.vladkhinski.homework_app.repository

import com.gmail.vladkhinski.homework_app.retrofit.BreakingBadApi
import com.gmail.vladkhinski.homework_app.retrofit.BreakingBadService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CharacterRepository(private val breakingBadApi: BreakingBadApi) {

    suspend fun getCharacters() = withContext(Dispatchers.IO) {
        BreakingBadService.provideBreakingBadApi().getCharacters()
    }

    suspend fun getCharacterDetails(id: Long) = withContext(Dispatchers.IO) {
        BreakingBadService.provideBreakingBadApi().getCharacterDetails(id)
    }
}
