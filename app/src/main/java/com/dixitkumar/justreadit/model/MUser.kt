package com.dixitkumar.justreadit.model

data class MUser(
    val id : String? = null,
    val userId : String = "",
    val userName : String ="",
    val userIconUrl: String ="",
    val phone : String = "",
    val email : String = "",
    val pageNo : String = "",
    val readingList : List<String>? = null,
    val wishlist : List<String>? = null,
    val recentSearched : List<String>? = null,
    val likedBooks : List<String>? = null,
    val dislikedBook : List<String>? = null
){
    fun toMap(): MutableMap<String,Any?>{
        return mutableMapOf(
            "user_id" to this.userId,
            "user_name" to this.userId,
            "user_icon_url" to this.userIconUrl,
            "user_email" to this.email,
            "phone" to this.phone,
            "reading_list" to this.readingList,
            "wishlist" to this.wishlist,
            "recent_searched" to this.recentSearched
        )
    }
}
