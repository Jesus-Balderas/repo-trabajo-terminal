package com.example.prototipo2tt.io

import com.example.prototipo2tt.models.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient


interface ApiService {

    @GET("laboratories")
    fun getLaboratories(): Call<ArrayList<Laboratory>>

    @GET("careers")
    fun getCareers(): Call<ArrayList<Career>>

    @GET("laboratories/{laboratory}/attendants")
    //Parametro de ruta:laboratory, laboratoryId de Tipo Int:Nombre del parametro que sirve como guía al llamar a esta función
    fun getAttendants(@Path("laboratory") laboratoryId: Int): Call<ArrayList<Attendant>>

    @GET("computerLaboratory/computers")
    fun getComputers(@Query("laboratory_id") id: Int): Call<ArrayList<Computer>>

    @GET("scheduleLaboratory/hours")
    fun getHours(@Query("laboratory_id")laboratoryId : Int, @Query("date") date: String):
            Call<ScheduleHour>

    @GET("attendants/reservations")
    fun getAttendantReservations(@Query("attendant_id") id: Int): Call<ArrayList<Reservation>>

    companion object Factory{
        private const val BASE_URL = "https://labscom.herokuapp.com/api/"
        fun create():ApiService{
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}