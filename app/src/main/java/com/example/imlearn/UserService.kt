package com.example.imlearn

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL="https://dummyjson.com/"

val retrofit: Retrofit? = null

 interface UserInterface{

     @GET("users")
     fun getUsers(@Query("page") page:Int) : Call<Contacts>
 }

object UserService{
    val userInstance: UserInterface
    init {
        val retrofit: Retrofit =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userInstance= retrofit.create(UserInterface::class.java)
    }

}



