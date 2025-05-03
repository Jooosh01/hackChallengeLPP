package com.onturaa.languagepairingprogram.retrofit

import com.onturaa.languagepairingprogram.model.Language
import com.onturaa.languagepairingprogram.model.PartnerRepository
import com.onturaa.languagepairingprogram.model.LoginRequest
import com.onturaa.languagepairingprogram.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("login/")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    )

    @POST("api/users/")
    suspend fun createUser(
        @Query("netID") netID: String? = "",
        @Query("name") name: String? = "",
        @Query("password") password: String? = "",
        @Query("level") level: String? = "",
        @Query("language") language: String? = "",
        @Query("description") description: String? = ""
    ): User

    @GET("api/languages/")
    suspend fun getLanguages(): List<Language>

    @POST("/api/matches/")
    suspend fun createMatch()

    @POST("/api/matches/{match_id}/accept/")
    suspend fun acceptMatch(@Path("match_id") matchId: Int)

    @DELETE("/api/users/{user_id}/")
    suspend fun deleteUser(@Path("user_id") userId: Int)

    @PUT("/api/users/{user_id}/match_status/")
    suspend fun updateMatchStatus(@Path("user_id") userId: Int)

    @POST("/api/matches/auto_match/")
    suspend fun autoMatch()

    @GET("/api/users/{user_id}/")
    suspend fun getUser(@Path("user_id") userId: Int)

    @GET("/api/users/")
    suspend fun getAllUsers(): List<PartnerRepository.Partner>

    @POST("/api/chatroom/")
    suspend fun createChatroom()

    @PUT("/api/chatroom/{chatroom_id}/")
    suspend fun closeChatroom(@Path("chatroom_id") chatroomId: Int)

    @POST("/api/chatroom/{chatroom_id}/messages/")
    suspend fun sendMessage(@Path("chatroom_id") chatroomId: Int)

    @GET("/api/chatroom/{chatroom_id}/messages/")
    suspend fun getMessageHistory(@Path("chatroom_id") chatroomId: Int)

    @GET("api/users/{user_id}/matches/")
    suspend fun getMatches(@Path("user_id") userId: Int): List<PartnerRepository.Partner>
}