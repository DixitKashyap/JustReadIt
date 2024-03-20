package com.dixitkumar.justreadit.model

data class MUser(
    val id : String? = null,
    val userId : String,
    val userName : String,
    val userIconUrl: String,
    val phone : String,
){
    fun toMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "user_id" to this.userId,
            "user_name" to this.userId,
            "user_icon_url" to this.userIconUrl,
            "phone" to this.phone
        )
    }
}
