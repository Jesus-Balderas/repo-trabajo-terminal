package com.example.prototipo2tt.io

import com.example.prototipo2tt.models.Attendant
import com.example.prototipo2tt.models.Computer
import com.example.prototipo2tt.models.Laboratory
import com.example.prototipo2tt.models.Reservation
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("laboratories")
    fun getLaboratories(): Call<ArrayList<Laboratory>>

    @GET("laboratories/{laboratory}/attendants")
    //Parametro de ruta:laboratory, laboratoryId de Tipo Int:Nombre del parametro que sirve como guía al llamar a esta función
    fun getAttendants(@Path("laboratory") laboratoryId: Int): Call<ArrayList<Attendant>>

    @GET("computerLaboratory/computers")
    fun getComputers(@Query("laboratory_id") id: Int): Call<ArrayList<Computer>>

    @GET("attendants/reservations")
    fun getAttendantReservations(@Query("attendant_id") id: Int): Call<ArrayList<Reservation>>

    companion object Factory{
        private const val BASE_URL = "https://labscom.herokuapp.com/api/"

        fun create():ApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}