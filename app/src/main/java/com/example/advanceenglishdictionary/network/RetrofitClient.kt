package com.example.advanceenglishdictionary.network

import com.example.advanceenglishdictionary.models.api.DictionaryResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

// ─────────────────────────────────────────────────────────────────────────────
//  FILE PURPOSE
//  This file has two things:
//    1. DictionaryApiService  — describes WHAT to call (the endpoint)
//    2. RetrofitClient        — describes HOW to call it (networking setup)
//
//  HOW TO USE (from a ViewModel or Repository):
//    val response = RetrofitClient.apiService.getWordDefinition("hello")
// ─────────────────────────────────────────────────────────────────────────────


// ── 1. API SERVICE INTERFACE ──────────────────────────────────────────────────
//
//  Retrofit reads this interface and generates the actual HTTP code for you.
//  You just define the endpoint with annotations — no manual HTTP code needed.

interface DictionaryApiService {

    /**
     * Fetches the definition of a word from the Free Dictionary API.
     *
     * HTTP call: GET https://api.dictionaryapi.dev/api/v2/entries/en/{word}
     *
     * @param word  The English word to look up (e.g. "hello").
     * @return      [Response] wrapping the list of results.
     *              Use response.isSuccessful to check for HTTP 200.
     *              HTTP 404 means the word was not found.
     */
    @GET("api/v2/entries/en/{word}")
    suspend fun getWordDefinition(
        @Path("word") word: String
    ): Response<List<DictionaryResponse>>
}


// ── 2. RETROFIT CLIENT (Singleton) ────────────────────────────────────────────
//
//  Using a Kotlin `object` makes this a singleton — only one instance
//  exists for the whole app lifetime. The `by lazy` keyword means each
//  property is created only the first time it is accessed.

object RetrofitClient {

    private const val BASE_URL        = "https://api.dictionaryapi.dev/"
    private const val TIMEOUT_SECONDS = 30L

    // Logs every request and response body to Logcat (useful for debugging)
    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // OkHttp is the underlying HTTP client Retrofit uses
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit instance — wires together the base URL, HTTP client, and JSON converter
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // converts JSON → data class
            .build()
    }

    /**
     * The single entry point to call the API.
     * Example:
     *   val result = RetrofitClient.apiService.getWordDefinition("hello")
     */
    val apiService: DictionaryApiService by lazy {
        retrofit.create(DictionaryApiService::class.java)
    }
}
