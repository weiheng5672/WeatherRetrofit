package com.example.weatherretrofit.network

import com.example.weatherretrofit.model.ApiResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

/*
 網路服務API
 */
private const val BASE_URL = "https://opendata.cwa.gov.tw/api/"


/*
 定義 Retrofit物件 串接網路API
 透過 kotlinx.serialization 解析 取得的JSON格式資料
 */
val json = Json { ignoreUnknownKeys = true }

val contentType = "application/json".toMediaType()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory(contentType))
    .baseUrl(BASE_URL)
    .build()



/*
 定義 我們自己想透過 Retrofit 實現的功能
 */
interface ApiService {

    @GET("v1/rest/datastore/C-B0025-001")
    suspend fun getData(
        @Query("Authorization") authorization: String,
        @Query("StationID") stationID: String,
        @Query("timeFrom") timeFrom: String,
        @Query("timeTo") timeTo: String,
    ): ApiResponse

}

/*
 這是一個 實現自定義介面 的 Retrofit物件
 程式的其他地方想從網路取得資料
 只需要使用 這個物件就可以了
 */
object Api {

    /*
     這個物件有一個屬性 指向實現了 ApiService 的物件
     */
    val retrofitService : ApiService by lazy {

        /*
         這個東西 就是 所謂的 實現了 ApiService 的物件
         by lazy 意味他被 延遲初始化
         */
        retrofit.create(ApiService::class.java)

    }

}