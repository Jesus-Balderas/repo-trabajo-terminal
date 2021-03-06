package com.example.prototipo2tt.io

import com.example.prototipo2tt.io.response.*
import com.example.prototipo2tt.models.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.http.*


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
    fun getAttendantReservations(@Header("Authorization") authHeader: String):
            Call<ArrayList<AttendantReservation>>

    @GET("attendants/reservations/history")
    fun getAttendantReservationsHistory(@Header("Authorization") authHeader: String):
            Call<ArrayList<AttendantReservation>>

    @GET("attendants/reservations/accept")
    fun getAttendantReservationsAccepted(@Header("Authorization") authHeader: String):
            Call<ArrayList<AttendantReservation>>

    @POST("login/student")
    fun postLoginStudent(@Query("num_boleta") boleta: String, @Query("password") password: String):
            Call<LoginStudentResponse>

    @POST("logout/student")
    fun postLogoutStudent(@Header("Authorization") authHeader: String): Call<Void>

    @GET("user/student")
    fun profileStudent(@Header("Authorization") authHeader: String): Call<ProfileStudentResponse>

    @POST("login/attendant")
    fun postLoginAttendant(@Query("num_empleado") numEmpleado: String, @Query("password") password: String):
            Call<LoginAttendantResponse>

    @POST("logout/attendant")
    fun postLogoutAttendant(@Header("Authorization") authHeader: String): Call<Void>

    @GET("user/attendant")
    fun profileAttendant(@Header("Authorization") authHeader: String): Call<ProfileAttendantResponse>

    @GET("students/reservations")
    fun getStudentReservations(@Header("Authorization") authHeader: String):
            Call<ArrayList<StudentReservation>>

    @GET("students/reservations/history")
    fun getStudentReservationsHistory(@Header("Authorization") authHeader: String):
            Call<ArrayList<StudentReservation>>

    @GET("students/reservations/accept")
    fun getStudentReservationsAccept(@Header("Authorization") authHeader: String):
            Call<ArrayList<StudentReservation>>

    @POST("reservation")
    fun storeStudentReservations(
        @Header("Authorization") authHeader: String,
        @Query("laboratory_id") laboratoryId: Int,
        @Query("attendant_id") attendantId: Int,
        @Query("computer_id") computerId: Int,
        @Query("scheduled_date") date: String,
        @Query("scheduled_hour") hour: String
    ): Call<StudentReservationResponse>

    @POST("reservation/{reservation}/cancel")
    fun postCancelReservationStudent(
        @Header("Authorization") authHeader: String,
        @Path("reservation") reservationId: Int
    ): Call<StudentReservationCancelResponse>

    @POST("reservation/{reservation}/reject")
    fun postRejectReservationAttendant(
        @Header("Authorization") authHeader: String,
        @Path("reservation") reservationId: Int
    ): Call<AttendantReservationResponse>

    @POST("reservation/{reservation}/accept")
    fun postAcceptReservationAttendant(
        @Header("Authorization") authHeader: String,
        @Path("reservation") reservationId: Int
    ): Call<AttendantReservationResponse>

    @GET("reservations/chart")
    fun getReservationsChart(
        @Header("Authorization") authHeader: String,
    ): Call<Chart>

    @POST("reservation/{reservation}/finish")
    fun postFinishReservationAttendant(
        @Header("Authorization") authHeader: String,
        @Path("reservation") reservationId: Int
    ): Call<AttendantReservationResponse>

    @POST("register/student")
    fun postRegisterStudent(
        @Query("num_boleta") boleta: String,
        @Query("name") name: String,
        @Query("first_name") firstName: String,
        @Query("second_name") secondName: String,
        @Query("email") email: String,
        @Query("career_id") careerId: Int,
        @Query("password") password: String
    ): Call<StudentReservationResponse>

    @POST("student/fcm/token")
    fun postTokenStudent(
        @Header("Authorization") authHeader: String,
        @Query("device_token") token : String,
    ): Call<Void>

    @POST("attendant/fcm/token")
    fun postTokenAttendant(
        @Header("Authorization") authHeader: String,
        @Query("device_token") token : String,
    ): Call<Void>

    @POST("attendant/fcm/sendNotification")
    fun postSendNotification(
        @Header("Authorization") authHeader: String,
        @Query("title") title : String,
        @Query("body") body : String,
    ): Call<AttendantReservationResponse>

    companion object Factory{
        private const val BASE_URL = "http://20.92.93.194/api/"
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