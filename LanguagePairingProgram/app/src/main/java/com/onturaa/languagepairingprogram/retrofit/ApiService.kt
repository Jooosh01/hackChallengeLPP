package com.onturaa.languagepairingprogram.retrofit

import com.onturaa.languagepairingprogram.model.PartnerRepository
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login/")
    suspend fun login(
        @Query("netID") netID: String? = "",
        @Query("password") password: String? = "",
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @GET("api/languages/")
    suspend fun getLanguages(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @POST("api/users/")
    suspend fun createUser(
        @Query("netID") netID: String? = "",
        @Query("name") name: String? = "",
        @Query("password") password: String? = "",
        @Query("level") level: String? = "",
        @Query("language_id") language_id: String? = "",
        @Query("description") description: String? = "",
        @Query("language") language: String? = "",
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @POST("api/matches/")
    suspend fun createMatch(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @POST("api/matches/<int:match_id>/accept/")
    suspend fun acceptMatch(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @DELETE("api/users/<int:user_id>/")
    suspend fun deleteUser(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @PUT("api/users/<int:user_id>/match_status/")
    suspend fun updateMatchStatus(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @POST("api/matches/auto_match/")
    suspend fun autoMatch(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @GET("api/users/<int:user_id>/")
    suspend fun getUser(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @GET("api/users/")
    suspend fun getAllUsers(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @POST("api/chatroom/")
    suspend fun createChatroom(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @PUT("api/chatroom/<int:chatroom_id>/")
    suspend fun closeChatroom(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @POST("api/chatroom/<int:chatroom_id>/messages/")
    suspend fun sendMessage(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )

    @GET("api/chatroom/<int:chatroom_id>/messages/")
    suspend fun getMessageHistory(
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    )
    @GET("api/users/<int:user_id>/matches/")
        suspend fun get_matches(
        @Path("user_id") userId: Int,
        @Header("Authorization") apiKey: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc0NjE1MzYwMiwianRpIjoiNDhlM2UyZDAtNzU5NC00NDk2LWE5MmQtZDRjYmMxNzIzZDM5IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjMiLCJuYmYiOjE3NDYxNTM2MDIsImV4cCI6MTc0NjE1NDUwMn0.BpHoWeENOjJsYXOtF3NY8n_zlCi0hc3-TBAflfyqzjU",
    ): List<PartnerRepository.Partner>

}

//"netID": "dan56",
//"name": "Daniel",
//"password": "dst888",
//"level": "beginner",
//"language_id": 2,
//"description": "Learning English",
//"language":"English"

//): SentimentScore
//data class SentimentScore(
//    val text: String,
//    val score: Float,
//    val sentiment: Sentiment
//)
//
//val emptySentimentScore = SentimentScore(
//    text = "",
//    score = 0f,
//    sentiment = Sentiment.NEUTRAL
//)
