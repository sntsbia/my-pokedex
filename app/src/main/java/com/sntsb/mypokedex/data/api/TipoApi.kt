package com.sntsb.mypokedex.data.api

import com.sntsb.mypokedex.data.api.response.TipoDetalhesResponse
import com.sntsb.mypokedex.data.api.response.TipoResponse
import retrofit2.http.GET
import retrofit2.http.Query

/** Interface que define os endpoints da API do aplicativo para TIPO **/
interface TipoApi {

    @GET("type/{id}")
    suspend fun getTypeById(@Query("id") id: Int): TipoDetalhesResponse

    @GET("type")
    suspend fun getTipos(): TipoResponse

    companion object {
        const val LIMIT = 20
    }

}