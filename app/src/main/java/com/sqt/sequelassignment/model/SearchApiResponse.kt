package com.sqt.sequelassignment.model

class SearchApiResponse{

    var posts: SearchResultResponse? = null
    var error: Throwable? = null

    constructor(posts: SearchResultResponse) {
        this.posts = posts
        this.error = null
    }

    constructor(error: Throwable) {
        this.error = error
        this.posts = null
    }
}