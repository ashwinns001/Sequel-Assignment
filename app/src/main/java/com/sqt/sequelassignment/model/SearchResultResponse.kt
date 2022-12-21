package com.sqt.sequelassignment.model

data class SearchResultResponse(
    val Search: ArrayList<SearchResultItem>,
    val totalResults: String,
    val Response: String
)
