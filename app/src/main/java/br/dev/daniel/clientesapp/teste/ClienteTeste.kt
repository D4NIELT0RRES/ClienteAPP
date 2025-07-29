package br.dev.daniel.clientesapp.teste

import br.dev.daniel.clientesapp.model.Cliente
import br.dev.daniel.clientesapp.service.ClienteService
import br.dev.daniel.clientesapp.service.RetrofitFactory

fun main() {

    val c1 = Cliente(
        nome = "Daniel Alves",
        email = "daniel@fight.com.br"
    )


    val retrofit = RetrofitFactory().getClienteService()
    val cliente = retrofit.gravar(c1)

}