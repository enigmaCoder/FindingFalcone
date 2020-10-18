package com.example.findingfalcone.gamenetwork

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers

import retrofit2.http.POST

interface IGameNetwork {

    @GET("/planets")
    fun getAvailablePlanets(): Observable<Response<ResponseBody>>

    @GET("/vehicles")
    fun getAvailableVehicles(): Observable<Response<ResponseBody>>

    @Headers("Accept: application/json")
    @POST("/token")
    fun getAccessToken(): Observable<Response<ResponseBody>>

    @Headers( "Accept: application/json","Content-Type: application/json")
    @POST("/find")
    fun findAlFalcone(@Body requestBody: RequestBody): Observable<Response<ResponseBody>>
}