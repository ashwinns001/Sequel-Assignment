package com.sqt.sequelassignment.model

class ApiResponse {

    var posts: MovieDetailResponse? = null
    var error: Throwable? = null

    constructor(posts: MovieDetailResponse) {
        this.posts = posts
        this.error = null
    }

    constructor(error: Throwable) {
        this.error = error
        this.posts = null
    }
}