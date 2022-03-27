package com.example.prototipo2tt.io

import com.example.prototipo2tt.models.Laboratory
import com.example.prototipo2tt.models.Reservation
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("laboratories")
    fun getLaboratories(): Call<ArrayList<Laboratory>>

    @GET("attendants/reservations")
    fun getAttendantReservations(@Query("attendant_id") id: Int): Call<ArrayList<Reservation>>

    companion object Factory{
        private const val BASE_URL = "http://10.0.0.50/api/"

        fun create():ApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}