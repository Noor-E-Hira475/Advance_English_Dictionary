package com.example.advanceenglishdictionary.models

import java.io.Serializable

data class WordDescription(
    val id: Int,
    val definition: String? = null,
    val category: String? = null,
    val synonyms: String? = null,
    val hyponyms: String? = null,
    val instanceHyponyms: String? = null,
    val hypernyms: String? = null,
    val instanceHypernyms: String? = null,
    val partHolonyms: String? = null,
    val memberHolonyms: String? = null,
    val substanceHolonyms: String? = null,
    val partMeronyms: String? = null,
    val memberMeronyms: String? = null,
    val substanceMeronyms: String? = null,
    val examples: String? = null,
    val antonyms: String? = null,
    val similar: String? = null,
    val also: String? = null
) : Serializable
