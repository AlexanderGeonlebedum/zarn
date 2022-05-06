package org.wit.zarn.api

 import org.wit.zarn.models.ZarnModel
 import retrofit2.Call
 import retrofit2.http.*

interface ZarnService {
//    @GET("/zarns")
//    fun getall(): Call<List<ZarnModel>>
//
//    @GET("/zarns/{id}")
//    fun get(@Path("id") id: String): Call<ZarnModel>
//
//    @DELETE("/zarns/{id}")
//    fun delete(@Path("id") id: String): Call<ZarnWrapper>
//
//    @POST("/zarns")
//    fun post(@Body zarn: ZarnModel): Call<ZarnWrapper>
//
//    @PUT("/zarns/{id}")
//    fun put(@Path("id") id: String,
//            @Body zarn: ZarnModel
//    ): Call<ZarnWrapper>


    @GET("/zarns")
    fun findall(): Call<List<ZarnModel>>

    @GET("/zarns/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<ZarnModel>>

    @GET("/zarns/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<ZarnModel>

    @DELETE("/zarns/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<ZarnWrapper>

    @POST("/zarns/{email}")
    fun post(@Path("email") email: String?,
             @Body zarn: ZarnModel)
            : Call<ZarnWrapper>

    @PUT("/zarns/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body donation: ZarnModel
    ): Call<ZarnWrapper>


}