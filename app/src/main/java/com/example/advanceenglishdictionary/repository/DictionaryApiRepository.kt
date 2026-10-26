package com.example.advanceenglishdictionary.repository

import com.example.advanceenglishdictionary.models.api.DictionaryResponse
import com.example.advanceenglishdictionary.network.RetrofitClient
import com.example.advanceenglishdictionary.ui.state.UiState

// ─────────────────────────────────────────────────────────────────────────────
//  DictionaryApiRepository
//
//  Sits between the ViewModel and RetrofitClient.
//  Its single job: call the API and translate the raw Retrofit response
//  into a UiState the ViewModel can observe.
//
//  HOW TO USE (from a ViewModel):
//    private val repository = DictionaryApiRepository()
//    val state = repository.lookUpWord("hello")   // returns UiState<…>
// ─────────────────────────────────────────────────────────────────────────────

class DictionaryApiRepository {

    private val apiService = RetrofitClient.apiService

    /**
     * Fetches word definitions from the Free Dictionary API.
     *
     * @param word  The word to look up (e.g. "ephemeral").
     * @return      [UiState.Success] containing the list of [DictionaryResponse] entries,
     *              [UiState.Error] if the network call fails or the word is not found.
     *
     * This is a suspend function — it must be called from a coroutine or another
     * suspend function (e.g. inside viewModelScope.launch).
     */
    suspend fun lookUpWord(word: String): UiState<List<DictionaryResponse>> {
        return try {
            val response = apiService.getWordDefinition(word.trim())

            if (response.isSuccessful) {
                val body = response.body()
                if (!body.isNullOrEmpty()) {
                    UiState.Success(body)
                } else {
                    // HTTP 200 but empty body — treat as not found
                    UiState.Error("No definitions found for \"$word\".")
                }
            } else {
                // HTTP 404 → word not found; other codes → generic error
                val errorMessage = when (response.code()) {
                    404  -> "Word \"$word\" not found in the dictionary."
                    429  -> "Too many requests. Please wait a moment and try again."
                    500  -> "The dictionary server is currently unavailable. Try later."
                    else -> "Error ${response.code()}: ${response.message()}"
                }
                UiState.Error(errorMessage)
            }
        } catch (e: java.net.UnknownHostException) {
            // Device is offline or DNS failed
            UiState.Error("No internet connection. Please check your network and try again.")
        } catch (e: java.net.SocketTimeoutException) {
            // Request took longer than the timeout configured in RetrofitClient
            UiState.Error("The request timed out. Please try again.")
        } catch (e: Exception) {
            // Catch-all for any other unexpected error
            UiState.Error("An unexpected error occurred: ${e.localizedMessage}")
        }
    }
}
