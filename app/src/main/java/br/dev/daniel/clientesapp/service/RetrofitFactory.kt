package br.dev.daniel.clientesapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val BASE_URL = "https://srv945707.hstgr.cloud/api/"

    private val retrofitFactory =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getClienteService(): ClienteService{
        return retrofitFactory.create(ClienteService::class.java)
    }

}