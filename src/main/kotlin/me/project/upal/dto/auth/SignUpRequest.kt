package me.project.upal.dto.auth

data class SignUpRequest(
        val email: String,
        val password: String,
        val phoneNumber: String,
        val nickName: String,
        val age: Int,
        val country: String,
        val interestTags: List<String>,
        val interestCountries: List<String>
)