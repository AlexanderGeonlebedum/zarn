package org.wit.zarn.api

 import org.wit.zarn.models.ZarnModel
 import retrofit2.Call
 import retrofit2.http.*

interface ZarnService {
    @GET("/zarns")
    fun getall(): Call<List<ZarnModel>>

    @GET("/zarns/{id}")
    fun get(@Path("id") id: String): Call<ZarnModel>

    @DELETE("/zarns/{id}")
    fun delete(@Path("id") id: String): Call<ZarnWrapper>

    @POST("/zarns")
    fun post(@Body zarn: ZarnModel): Call<ZarnWrapper>

    @PUT("/zarns/{id}")
    fun put(@Path("id") id: String,
            @Body zarn: ZarnModel
    ): Call<ZarnWrapper>




}