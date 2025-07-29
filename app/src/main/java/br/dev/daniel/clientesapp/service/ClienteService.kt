package br.dev.daniel.clientesapp.service

import br.dev.daniel.clientesapp.model.Cliente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteService {

    @POST("clientes")
    fun gravar(@Body cliente: Cliente): Call<Cliente>

    @GET("clientes")
    fun exibirTodos(): Call<List<Cliente>>

    @PUT("clientes")
    fun atualizar(@Body cliente: Cliente): Call<Cliente>

    @DELETE("clientes/{id}")
    fun excluir (@Path("id") id: Long): Call<Unit>

    @GET("clientes/{id}")
    fun exibirPorID(@Path("id") id: Long): Call<Cliente>
}