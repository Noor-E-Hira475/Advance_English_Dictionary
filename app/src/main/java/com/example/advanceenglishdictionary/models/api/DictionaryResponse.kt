package com.example.advanceenglishdictionary.models.api

import com.google.gson.annotations.SerializedName

// ─────────────────────────────────────────────────────────────
//  These data classes mirror the JSON returned by the API.
//  Example URL: https://api.dictionaryapi.dev/api/v2/entries/en/hello
//
//  The JSON structure (simplified):
//
//  [                              ← List<DictionaryResponse>
//    {
//      "word": "hello",
//      "phonetic": "həˈləʊ",
//      "origin": "early 19th century...",
//      "phonetics": [             ← List<Phonetic>
//        { "text": "həˈləʊ", "audio": "https://..." }
//      ],
//      "meanings": [              ← List<Meaning>
//        {
//          "partOfSpeech": "noun",
//          "definitions": [       ← List<Definition>
//            {
//              "definition": "a greeting",
//              "example": "say hello",
//              "synonyms": [],
//              "antonyms": []
//            }
//          ]
//        }
//      ]
//    }
//  ]
// ─────────────────────────────────────────────────────────────

/** Represents one word entry from the API response. */
data class DictionaryResponse(
    @SerializedName("word")      val word: String,
    @SerializedName("phonetic")  val phonetic: String?,       // e.g. "həˈləʊ"
    @SerializedName("origin")    val origin: String?,         // e.g. "early 19th century..."
    @SerializedName("phonetics") val phonetics: List<Phonetic>,
    @SerializedName("meanings")  val meanings: List<Meaning>
)

/** One phonetic entry — text representation and optional audio URL. */
data class Phonetic(
    @SerializedName("text")  val text: String?,
    @SerializedName("audio") val audio: String?
)

/** A group of definitions sharing the same part of speech (noun, verb, etc.). */
data class Meaning(
    @SerializedName("partOfSpeech") val partOfSpeech: String,
    @SerializedName("definitions")  val definitions: List<Definition>
)

/** A single definition with an optional example sentence, synonyms, and antonyms. */
data class Definition(
    @SerializedName("definition") val definition: String,
    @SerializedName("example")   val example: String?,
    @SerializedName("synonyms")  val synonyms: List<String>,
    @SerializedName("antonyms")  val antonyms: List<String>
)
